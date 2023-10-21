package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.databinding.FragmentSpellsBinding
import com.jlahougue.dndcharactersheet.extensions.observeNonNull
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.fragments.spells.clazz.DialogClassDetails
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class SpellsFragment : Fragment(),
    SpellAdapter.SpellListener,
    DialogSpellDetails.DialogSpellDetailsListener,
    ClassFilterAdapter.ClassFilterListener,
    SpellLevelAdapter.SpellLevelListener
{

    private var _binding: FragmentSpellsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val main: MainActivity by lazy { activity as MainActivity }

    private val spellsViewModel: SpellsViewModel by viewModels()

    private val spellLevelAdapter by lazy { SpellLevelAdapter(this) }
    private val spellAdapter by lazy { SpellAdapter(this) }

    private val classFilterAdapter by lazy { ClassFilterAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpellsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.ability = AbilityRepository.Companion

        initializeSpellSearchBar()

        binding.recyclerSpellLevelsFilter.adapter = spellLevelAdapter
        binding.recyclerSpells.adapter = spellAdapter

        binding.buttonEdit.setOnClickListener { spellsViewModel.setEditMode(true) }
        binding.buttonDone.setOnClickListener { spellsViewModel.setEditMode(false) }

        main.mainViewModel.characterID.observeOnce(viewLifecycleOwner) {
            spellsViewModel.characterID = it

            binding.viewModel = spellsViewModel

            spellsViewModel.spellLevels.observe(viewLifecycleOwner) { spellLevels ->
                spellLevelAdapter.spellLevels = spellLevels
            }

            spellsViewModel.spells.observeNonNull(viewLifecycleOwner) { spells ->
                spellAdapter.spells = spells
            }
        }

        spellsViewModel.editMode.observe(viewLifecycleOwner) { editMode ->
            spellAdapter.editMode = editMode
        }

        spellsViewModel.spellLevel.observe(viewLifecycleOwner) { spellLevel ->
            spellLevelAdapter.activeLevel = spellLevel
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeSpellSearchBar() {
        binding.editSpellsSearch.addTextChangedListener {
            spellAdapter.search = it.toString()
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

    override fun onSpellLevelClick(position: Int) {
        spellsViewModel.setSpellLevel(position)
    }

    override fun onClassClicked(clazz: Class) {
        DialogClassDetails(clazz).show(
            main.supportFragmentManager,
            DialogClassDetails.TAG
        )
    }
}