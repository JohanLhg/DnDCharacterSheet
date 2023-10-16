package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.entities.views.SkillView
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.databinding.FragmentStatsBinding
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.fragments.stats.StatsViewModel.Companion.CURRENT
import com.jlahougue.dndcharactersheet.ui.fragments.stats.StatsViewModel.Companion.TEMPORARY
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class StatsFragment : Fragment(),
    AbilityAdapter.OnAbilityChangedListener,
    SkillAdapter.OnSkillChangedListener,
    SavingThrowAdapter.OnAbilityChangedListener {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val main: MainActivity by lazy { activity as MainActivity }

    private val statsViewModel: StatsViewModel  by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(StatsViewModel::class.java)
    }

    private val skillAdapter: SkillAdapter by lazy {
        SkillAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        val abilityAdapter = AbilityAdapter(this)
        binding.recyclerAbilities.adapter = abilityAdapter

        binding.recyclerSkills.adapter = skillAdapter

        val savingThrowAdapter = SavingThrowAdapter(this)
        binding.recyclerSavingThrows.adapter = savingThrowAdapter

        binding.columnHealth.labelCurrentHealth.setOnClickListener {
            statsViewModel.healthMode.value = CURRENT
        }

        binding.columnHealth.labelTemporaryHealth.setOnClickListener {
            statsViewModel.healthMode.value = TEMPORARY
        }

        main.mainViewModel.characterID.observe(viewLifecycleOwner) { characterID ->
            statsViewModel.characterID = characterID

            statsViewModel.skills.observe(viewLifecycleOwner) {
                skillAdapter.skills = it
            }

            statsViewModel.abilities.observe(viewLifecycleOwner) {
                abilityAdapter.abilities = it
                savingThrowAdapter.abilities = it

                val dexterity = it.find { ability -> ability.name == DEXTERITY }
                binding.textInitiativeModifier.text = main.getString(R.string.plus_value, dexterity?.modifier)
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

        statsViewModel.healthMode.observe(viewLifecycleOwner) {
            when (it) {
                CURRENT -> {
                    binding.columnHealth.labelCurrentHealth.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.text))
                    binding.columnHealth.labelTemporaryHealth.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.text_light))
                    binding.columnHealth.layoutTemporaryHealth.visibility = View.GONE
                    binding.columnHealth.layoutCurrentHealth.visibility = View.VISIBLE
                }
                TEMPORARY -> {
                    binding.columnHealth.labelCurrentHealth.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.text_light))
                    binding.columnHealth.labelTemporaryHealth.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.text))
                    binding.columnHealth.layoutCurrentHealth.visibility = View.GONE
                    binding.columnHealth.layoutTemporaryHealth.visibility = View.VISIBLE
                }
            }
        }

        statsViewModel.health.observeOnce(viewLifecycleOwner) {
            binding.columnHealth.editCurrent.setText(it.currentHp.toString())
            binding.columnHealth.editMax.setText(it.maxHp.toString())
            binding.columnHealth.editTemporaryHealth.setText(it.temporaryHp.toString())
            val spinnerValues = resources.getStringArray(R.array.dice)
            binding.columnHealth.spinnerHitDice.setSelection(spinnerValues.indexOf(it.hitDice))
        }

        statsViewModel.deathSaves.observeOnce(viewLifecycleOwner) {
            setDeathSavesFailures(it.failures)
            setDeathSavesSuccesses(it.successes)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeListeners() {
        initializeSkillsListeners()
        initializeStatsListeners()
        initializeHealthListeners()
        initializeDeathSavesListeners()
    }

    private fun initializeSkillsListeners() {
        binding.editSkillsSearch.addTextChangedListener {
            skillAdapter.filter(it.toString())
        }
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
            binding.columnHealth.editCurrent.setText(health.currentHp.toString())
        }

        binding.columnHealth.buttonCurrentHealthMinus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.currentHp--
            binding.columnHealth.editCurrent.setText(health.currentHp.toString())
        }

        binding.columnHealth.editMax.addTextChangedListener {
            val health = statsViewModel.health.value!!
            health.maxHp = it.toString().toInt()
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonMaxHealthPlus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.maxHp++
            binding.columnHealth.editMax.setText(health.maxHp.toString())
        }

        binding.columnHealth.buttonMaxHealthMinus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.maxHp--
            binding.columnHealth.editMax.setText(health.maxHp.toString())
        }

        binding.columnHealth.editTemporaryHealth.addTextChangedListener {
            val health = statsViewModel.health.value!!
            health.temporaryHp = it.toString().toInt()
            statsViewModel.updateHealth(health)
        }

        binding.columnHealth.buttonTemporaryHealthPlus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.temporaryHp++
            binding.columnHealth.editTemporaryHealth.setText(health.temporaryHp.toString())
        }

        binding.columnHealth.buttonTemporaryHealthMinus.setOnClickListener {
            val health = statsViewModel.health.value!!
            health.temporaryHp--
            binding.columnHealth.editTemporaryHealth.setText(health.temporaryHp.toString())
        }

        binding.columnHealth.spinnerHitDice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                if (parent != null && statsViewModel.health.value != null) {
                    val health = statsViewModel.health.value!!
                    health.hitDice = parent.getItemAtPosition(position).toString()
                    statsViewModel.updateHealth(health)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initializeDeathSavesListeners() {
        binding.columnHealth.checkBoxFailure1.setOnClickListener { setDeathSavesFailures(1) }
        binding.columnHealth.checkBoxFailure2.setOnClickListener { setDeathSavesFailures(2) }
        binding.columnHealth.checkBoxFailure3.setOnClickListener { setDeathSavesFailures(3) }
        binding.columnHealth.checkBoxSuccess1.setOnClickListener { setDeathSavesSuccesses(1) }
        binding.columnHealth.checkBoxSuccess2.setOnClickListener { setDeathSavesSuccesses(2) }
        binding.columnHealth.checkBoxSuccess3.setOnClickListener { setDeathSavesSuccesses(3) }
    }

    private fun setDeathSavesFailures(failures: Int) {
        val deathSaves = statsViewModel.deathSaves.value!!
        if (deathSaves.failures >= failures) deathSaves.failures = failures - 1
        else deathSaves.failures = failures
        statsViewModel.updateDeathSaves(deathSaves)

        binding.columnHealth.checkBoxFailure1.isChecked = deathSaves.failures >= 1
        binding.columnHealth.checkBoxFailure2.isChecked = deathSaves.failures >= 2
        binding.columnHealth.checkBoxFailure3.isChecked = deathSaves.failures >= 3
    }

    private fun setDeathSavesSuccesses(successes: Int) {
        val deathSaves = statsViewModel.deathSaves.value!!
        if (deathSaves.successes >= successes) deathSaves.successes = successes - 1
        else deathSaves.successes = successes
        statsViewModel.updateDeathSaves(deathSaves)

        binding.columnHealth.checkBoxSuccess1.isChecked = deathSaves.successes >= 1
        binding.columnHealth.checkBoxSuccess2.isChecked = deathSaves.successes >= 2
        binding.columnHealth.checkBoxSuccess3.isChecked = deathSaves.successes >= 3
    }

    override fun onSkillProficiencyChanged(skill: SkillView) {
        statsViewModel.updateSkill(skill)
    }

    override fun onAbilityValueChanged(ability: AbilityView) {
        statsViewModel.updateAbilityValue(ability)
    }

    override fun onAbilityProficiencyChanged(ability: AbilityView) {
        statsViewModel.updateAbilityProficiency(ability)
    }
}