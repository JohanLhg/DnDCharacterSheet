package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityModifierView
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

            statsViewModel.abilityModifiers.observe(viewLifecycleOwner) {
                savingThrowAdapter.abilities = it
            }
        }

        statsViewModel.abilities.observeOnce(viewLifecycleOwner) {
            abilityAdapter.abilities = it
            val dexterity = it.find { ability -> ability.name == DEXTERITY }
            binding.textInitiativeModifier.text = dexterity?.getModifier().toString()
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

    override fun onAbilityChanged(ability: Ability) {
        statsViewModel.updateAbilityProficiency(ability)
    }

    override fun onSkillChanged(skill: SkillView) {
        statsViewModel.updateSkill(skill)
    }

    override fun onAbilityChanged(ability: AbilityModifierView) {
        statsViewModel.updateAbilityProficiency(ability)
    }
}