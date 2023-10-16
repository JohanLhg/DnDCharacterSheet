package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.databinding.FragmentSpellsBinding
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class SpellsFragment : Fragment(),
    SpellAdapter.SpellListener,
    DialogSpellDetails.DialogSpellDetailsListener,
    ClassFilterAdapter.ClassFilterListener {

    private var _binding: FragmentSpellsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val main: MainActivity by lazy { activity as MainActivity }

    private val spellsViewModel: SpellsViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(SpellsViewModel::class.java)
    }

    private val spellLevelAdapter by lazy { SpellLevelAdapter(this) }

    private val classFilterAdapter by lazy { ClassFilterAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpellsBinding.inflate(inflater, container, false)

        initializeSpellSearchBar()

        main.mainViewModel.characterID.observe(viewLifecycleOwner) {
            if (it == 0L) return@observe
            spellsViewModel.characterID = it

            spellsViewModel.spellcasting.observe(viewLifecycleOwner) { spellcasting ->
                val ability = AbilityRepository.getModifierName(requireContext(), spellcasting.ability)
                binding.textSpellcastingAbility.text = main.getString(R.string.text_spellcasting_ability, ability, spellcasting.modifier)
                binding.textSpellSaveDC.text = spellcasting.saveDC.toString()
                binding.textSpellAttackBonus.text = spellcasting.attackBonus.toString()
            }

            spellsViewModel.characterSpellStats.observe(viewLifecycleOwner) { stats ->
                binding.textTotalUnlocked.text = stats.totalUnlocked.toString()
                binding.textTotalPrepared.text = main.getString(R.string.text_prepared, stats.totalPrepared, stats.maxPrepared)
                binding.textTotalHighlighted.text = stats.totalHighlighted.toString()
            }

            spellsViewModel.characterLevel.observeOnce(viewLifecycleOwner) { characterLevel ->
                spellLevelAdapter.characterLevel = characterLevel
                binding.recyclerSpellLevels.adapter = spellLevelAdapter

                spellsViewModel.spells.observe(viewLifecycleOwner) { spellLevels ->
                    if (spellLevels != null)
                        spellLevelAdapter.spellLevels = spellLevels
                }

                binding.buttonEdit.setOnClickListener { spellsViewModel.setEditMode(true) }
                binding.buttonDone.setOnClickListener { spellsViewModel.setEditMode(false) }
            }
        }

        spellsViewModel.editMode.observe(viewLifecycleOwner) { editMode ->
            spellLevelAdapter.editMode = editMode
            if (editMode) {
                binding.buttonEdit.visibility = View.GONE
                binding.buttonDone.visibility = View.VISIBLE
            } else {
                binding.buttonEdit.visibility = View.VISIBLE
                binding.buttonDone.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeSpellSearchBar() {
        binding.editSpellsSearch.addTextChangedListener {
            spellLevelAdapter.search = it.toString()
        }
    }

    override fun onSpellClick(spell: SpellWithCharacterInfo) {
        DialogSpellDetails(spell, this).show(
            main.supportFragmentManager,
            DialogSpellDetails.TAG
        )
    }

    override fun updateCharacterSpell(characterSpell: CharacterSpell) {
        spellsViewModel.updateCharacterSpell(characterSpell)
    }

    override fun onSpellDetailsClosed() = spellsViewModel.refresh()

    override fun onFilterChange(filteredClasses: List<String>) {

    }
}