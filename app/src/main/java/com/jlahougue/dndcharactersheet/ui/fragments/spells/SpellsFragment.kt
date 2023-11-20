package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.FragmentSpellsBinding
import com.jlahougue.dndcharactersheet.extensions.collectLatestLifecycleFlow
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.detailsDialogs.DialogClassDetails
import com.jlahougue.dndcharactersheet.ui.elements.SearchBarListener
import com.jlahougue.dndcharactersheet.ui.fragments.spells.spellAdapters.SpellAdapter
import com.jlahougue.dndcharactersheet.ui.fragments.spells.spellAdapters.SpellLevelAdapter
import com.jlahougue.dndcharactersheet.ui.fragments.spells.spellAdapters.SpellLevelFilterAdapter
import com.jlahougue.dndcharactersheet.ui.fragments.spells.spellDetails.SpellDetailsDialog
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class SpellsFragment : Fragment(),
    SpellAdapter.Companion.SpellListener,
    SpellDetailsDialog.DialogSpellDetailsListener,
    ClassFilterAdapter.ClassFilterListener,
    SpellLevelFilterAdapter.Companion.SpellLevelFilterListener,
    SpellLevelAdapter.Companion.SpellLevelListener
{

    private var _binding: FragmentSpellsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val main: MainActivity by lazy { activity as MainActivity }

    private val spellsViewModel: SpellsViewModel by viewModels()

    private val classFilterAdapter by lazy { ClassFilterAdapter(this) }
    private val spellLevelFilterAdapter by lazy { SpellLevelFilterAdapter(this) }
    private val spellAdapter by lazy { SpellAdapter(this) }
    private val allSpellsAdapter by lazy { SpellLevelAdapter(this, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpellsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        initializeSpellSearchBar()

        binding.recyclerClassFilter.adapter = classFilterAdapter
        binding.recyclerSpellLevelsFilter.adapter = spellLevelFilterAdapter
        binding.recyclerEditSpells.adapter = spellAdapter
        binding.recyclerSpells.adapter = allSpellsAdapter

        binding.buttonUncheckAll.setOnClickListener { classFilterAdapter.uncheckAll() }

        binding.buttonEdit.setOnClickListener { spellsViewModel.setEditMode(true) }
        binding.buttonDone.setOnClickListener { spellsViewModel.setEditMode(false) }

        main.mainViewModel.characterID.observeOnce(viewLifecycleOwner) {
            spellsViewModel.characterID = it

            binding.viewModel = spellsViewModel

            spellsViewModel.spellSlots.observe(viewLifecycleOwner) { spellLevels ->
                spellLevelFilterAdapter.spellLevels = spellLevels
            }

            collectLatestLifecycleFlow(spellsViewModel.spellLevels) { spellLevels ->
                allSpellsAdapter.levels = spellLevels
            }
        }

        spellsViewModel.classes.observe(viewLifecycleOwner) { classes ->
            classFilterAdapter.classes = classes
        }

        collectLatestLifecycleFlow(spellsViewModel.spellLevel) { spellLevel ->
            spellLevelFilterAdapter.activeLevel = spellLevel
        }

        collectLatestLifecycleFlow(spellsViewModel.filteredEditSpells) { filteredSpells ->
            spellAdapter.spells = filteredSpells
        }

        collectLatestLifecycleFlow(spellsViewModel.filteredLevels) { filteredMapSpells ->
            allSpellsAdapter.levels = filteredMapSpells
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeSpellSearchBar() {
        binding.includeSpellsSearch.apply {
            accentColor = main.getColor(R.color.spell)

            listener = object : SearchBarListener {
                override fun focusOnSearch() { editSearch.requestFocus() }
                override fun clearSearch() { editSearch.text.clear() }
            }

            editSearch.addTextChangedListener {
                spellsViewModel.search = it.toString()
            }

            editSearch.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                focused = hasFocus
            }
        }
    }

    override fun onSpellClick(spell: SpellWithCharacterInfo) {
        SpellDetailsDialog(spell, this).show(
            main.supportFragmentManager,
            SpellDetailsDialog.TAG
        )
    }

    override fun updateCharacterSpell(characterSpell: CharacterSpell) {
        spellsViewModel.updateCharacterSpell(characterSpell)
    }

    override fun onSpellDetailsClosed() = spellsViewModel.refresh()

    override fun onFilterChange(filteredClasses: List<String>) {
        spellsViewModel.classFilter = filteredClasses
    }

    override fun onSpellLevelClick(position: Int) {
        spellsViewModel.setSpellLevel(position)
    }

    override fun updateSpellSlot(spellSlot: SpellSlot) {
        spellsViewModel.updateSpellSlot(spellSlot)
    }

    override fun onClassClicked(clazz: Class) {
        DialogClassDetails(clazz).show(
            main.supportFragmentManager,
            DialogClassDetails.TAG
        )
    }
}