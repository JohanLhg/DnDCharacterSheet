package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.entities.views.SkillView
import com.jlahougue.dndcharactersheet.databinding.FragmentStatsBinding
import com.jlahougue.dndcharactersheet.extensions.addFocusedTextChangedListener
import com.jlahougue.dndcharactersheet.extensions.observeNonNull
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.elements.SearchBarListener
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

    private val statsViewModel: StatsViewModel by viewModels()

    private val abilityAdapter: AbilityAdapter by lazy {
        AbilityAdapter(this)
    }
    private val skillAdapter: SkillAdapter by lazy {
        SkillAdapter(this)
    }
    private val savingThrowAdapter: SavingThrowAdapter by lazy {
        SavingThrowAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerAbilities.adapter = abilityAdapter
        binding.recyclerSkills.adapter = skillAdapter
        binding.recyclerSavingThrows.adapter = savingThrowAdapter

        main.mainViewModel.characterID.observeOnce(viewLifecycleOwner) { characterID ->
            statsViewModel.characterID = characterID
            
            binding.viewModel = statsViewModel

            statsViewModel.skills.observe(viewLifecycleOwner) {
                skillAdapter.skills = it
            }

            statsViewModel.abilities.observe(viewLifecycleOwner) {
                abilityAdapter.abilities = it
                savingThrowAdapter.abilities = it
            }

            statsViewModel.health.observeNonNull(viewLifecycleOwner) {
                val spinnerValues = resources.getStringArray(R.array.dice)
                binding.columnHealth.spinnerHitDice.setSelection(spinnerValues.indexOf(it.hitDice))
            }

            initializeListeners()
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
        binding.includeSkillsSearch.apply {
            accentColor = main.getColor(R.color.spell)

            listener = object : SearchBarListener {
                override fun focusOnSearch() { editSearch.requestFocus() }
                override fun clearSearch() { editSearch.text.clear() }
            }

            editSearch.addTextChangedListener {
                skillAdapter.filter(it.toString())
            }

            editSearch.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                focused = hasFocus
            }
        }
    }

    private fun initializeStatsListeners() {
        binding.editValueSpeed.addTextChangedListener {
            val stats = statsViewModel.stats.value!!
            stats.speed = getInt(it.toString())
            statsViewModel.updateStats(stats)
        }

        binding.editValueArmorClass.addTextChangedListener {
            val stats = statsViewModel.stats.value!!
            stats.armorClass = getInt(it.toString())
            statsViewModel.updateStats(stats)
        }
    }

    private fun initializeHealthListeners() {
        binding.columnHealth.apply {
            labelCurrentHealth.setOnClickListener {
                statsViewModel.healthMode.value = CURRENT
            }

            labelTemporaryHealth.setOnClickListener {
                statsViewModel.healthMode.value = TEMPORARY
            }

            editCurrent.addTextChangedListener {
                statsViewModel.updateHealth(currentHp = getInt(it.toString()))
            }

            buttonCurrentHealthPlus.setOnClickListener {
                statsViewModel.updateHealth(addToCurrentHp = 1)
            }

            buttonCurrentHealthMinus.setOnClickListener {
                statsViewModel.updateHealth(addToCurrentHp = -1)
            }

            editMax.addFocusedTextChangedListener {
                statsViewModel.updateHealth(maxHp = getInt(it.toString()))
            }

            buttonMaxHealthPlus.setOnClickListener {
                statsViewModel.updateHealth(addToMaxHp = 1)
            }

            buttonMaxHealthMinus.setOnClickListener {
                statsViewModel.updateHealth(addToMaxHp = -1)
            }

            editTemporaryHealth.addFocusedTextChangedListener {
                statsViewModel.updateHealth(temporaryHp = getInt(it.toString()))
            }

            buttonTemporaryHealthPlus.setOnClickListener {
                statsViewModel.updateHealth(addToTemporaryHp = 1)
            }

            buttonTemporaryHealthMinus.setOnClickListener {
                statsViewModel.updateHealth(addToTemporaryHp = -1)
            }

            buttonTemporaryHealthClear.setOnClickListener {
                statsViewModel.updateHealth(temporaryHp = 0)
            }

            spinnerHitDice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (parent != null && statsViewModel.health.value != null) {
                        val hitDice = parent.getItemAtPosition(position).toString()
                        statsViewModel.updateHealth(hitDice = hitDice)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            buttonLongRest.setOnClickListener {
                statsViewModel.longRest()
            }
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
        statsViewModel.deathSaves.value = deathSaves
        statsViewModel.updateDeathSaves(deathSaves)
    }

    private fun setDeathSavesSuccesses(successes: Int) {
        val deathSaves = statsViewModel.deathSaves.value!!
        if (deathSaves.successes >= successes) deathSaves.successes = successes - 1
        else deathSaves.successes = successes
        statsViewModel.deathSaves.value = deathSaves
        statsViewModel.updateDeathSaves(deathSaves)
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

    private fun getInt(value: String): Int {
        return if (value == "") 0 else value.toInt()
    }
}