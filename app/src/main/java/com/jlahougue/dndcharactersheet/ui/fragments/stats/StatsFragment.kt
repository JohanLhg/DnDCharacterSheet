package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.room.views.SkillView
import com.jlahougue.dndcharactersheet.databinding.FragmentStatsBinding
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class StatsFragment : Fragment(),
    AbilityAdapter.OnAbilityChangedListener,
    SkillAdapter.OnSkillChangedListener,
    SavingThrowAdapter.OnAbilityChangedListener {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val statsViewModel: StatsViewModel  by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(StatsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        val abilityAdapter = AbilityAdapter(this)
        binding.recyclerAbilities.adapter = abilityAdapter
        binding.recyclerAbilities.layoutManager = LinearLayoutManager(requireContext())

        val skillAdapter = SkillAdapter(this)
        binding.recyclerSkills.adapter = skillAdapter
        binding.recyclerSkills.layoutManager = LinearLayoutManager(requireContext())

        val savingThrowAdapter = SavingThrowAdapter(this)
        binding.recyclerSavingThrows.adapter = savingThrowAdapter
        binding.recyclerSavingThrows.layoutManager = LinearLayoutManager(requireContext())

        (activity as MainActivity).mainViewModel.characterID.observe(viewLifecycleOwner) { characterID ->
            statsViewModel.characterID = characterID

            statsViewModel.skills.observe(viewLifecycleOwner) {
                skillAdapter.skills = it
            }

            statsViewModel.abilities.observe(viewLifecycleOwner) {
                abilityAdapter.abilities = it
                savingThrowAdapter.abilities = it

                val dexterity = it.find { ability -> ability.name == DEXTERITY }
                binding.textInitiativeModifier.text = dexterity?.modifier.toString()
            }

            statsViewModel.proficiency.observe(viewLifecycleOwner) {
                binding.textProficiencyBonus.text = requireContext().getString(R.string.plus_value, it)
            }

            statsViewModel.hitDiceNbr.observe(viewLifecycleOwner) {
                binding.columnHealth.textHitDiceNbr.text = it.toString()
            }

            initializeListeners()
        }

        statsViewModel.stats.observeOnce(viewLifecycleOwner) {
            binding.editValueArmorClass.setText(it.armorClass.toString())
            binding.editValueSpeed.setText(it.speed.toString())
        }

        statsViewModel.health.observeOnce(viewLifecycleOwner) {
            binding.columnHealth.editCurrent.setText(it.currentHp.toString())
            binding.columnHealth.editMax.setText(it.maxHp.toString())
            binding.columnHealth.editTemporaryHealth.setText(it.temporaryHp.toString())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeListeners() {
        initializeStatsListeners()
        initializeHealthListeners()
    }

    private fun initializeStatsListeners() {
        binding.editValueSpeed.addTextChangedListener {
            val stats = statsViewModel.stats.value!!
            stats.speed = it.toString().toInt()
            statsViewModel.updateStats(stats)
        }

        binding.editValueArmorClass.addTextChangedListener {
            val stats = statsViewModel.stats.value!!
            stats.armorClass = it.toString().toInt()
            statsViewModel.updateStats(stats)
        }
    }

    private fun initializeHealthListeners() {
        binding.columnHealth.editCurrent.addTextChangedListener {
            val health = statsViewModel.health.value!!
            health.currentHp = it.toString().toInt()
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonCurrentHealthPlus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.currentHp++
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonCurrentHealthMinus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.currentHp--
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.editMax.addTextChangedListener {
            val health = statsViewModel.health.value!!
            health.maxHp = it.toString().toInt()
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonMaxHealthPlus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.maxHp++
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonMaxHealthMinus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.maxHp--
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.editTemporaryHealth.addTextChangedListener {
            val health = statsViewModel.health.value!!
            health.temporaryHp = it.toString().toInt()
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonTemporaryHealthPlus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.temporaryHp++
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonTemporaryHealthMinus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.temporaryHp--
            statsViewModel.updateHealth(health)
        }
    }

    override fun onSkillChanged(skill: SkillView) {
        statsViewModel.updateSkill(skill)
    }

    override fun onAbilityValueChanged(ability: AbilityView) {
        statsViewModel.updateAbilityValue(ability)
    }

    override fun onAbilityProficiencyChanged(ability: AbilityView) {
        statsViewModel.updateAbilityProficiency(ability)
    }
}