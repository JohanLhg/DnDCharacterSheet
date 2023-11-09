package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellLevel
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.entities.views.CharacterSpellStatsView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.ClassRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellSlotRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellcastingRepository
import com.jlahougue.dndcharactersheet.domainLayer.GetSpellLevelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val classRepository = ClassRepository(application)
    private val characterSpellRepository = CharacterSpellRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellSlotRepository = SpellSlotRepository(application)
    private val spellRepository = SpellRepository(application)

    private val getSpellLevelUseCase = GetSpellLevelUseCase(application)

    lateinit var spellcasting: LiveData<SpellcastingView>
    lateinit var characterSpellStats: LiveData<CharacterSpellStatsView>
    val classes = MutableLiveData<List<String>>(listOf())

    private val _spellLevel = MutableStateFlow(0)
    val spellLevel = _spellLevel.asStateFlow()

    lateinit var spellSlots: LiveData<List<SpellSlotView>>

    lateinit var spellLevels: StateFlow<List<SpellLevel>>

    // Unlocked spells
    private val _filteredLevels = MutableStateFlow<List<SpellLevel>>(listOf())
    val filteredLevels = _filteredLevels.asStateFlow()

    // Edit mode
    private val _editSpells = MutableStateFlow<List<SpellWithCharacterInfo>>(listOf())
    private val editSpells = _editSpells.asStateFlow()

    private val _filteredEditSpells = MutableStateFlow<List<SpellWithCharacterInfo>>(listOf())
    val filteredEditSpells = _filteredEditSpells.asStateFlow()

    private val _editMode = MutableStateFlow(false)
    val editMode = _editMode.asStateFlow()

    var classFilter = listOf<String>()
        set(value) {
            field = value
            updateSpellFilters()
        }
    var search = ""
        set(value) {
            field = value
            updateSpellFilters()
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            classes.postValue(classRepository.getNames())
        }
        viewModelScope.launch {
            editSpells.collect {
                updateSpellFilters()
            }
        }
        viewModelScope.launch {
            spellLevel.collect {
                updateSpellList()
            }
        }
        viewModelScope.launch {
            editMode.collect {
                updateSpellList()
            }
        }
    }

    var characterID = 0L
        set(value) {
            field = value
            spellcasting = spellcastingRepository.get(value)
            characterSpellStats = characterSpellRepository.getCharacterSpellStats(value)
            spellSlots = spellSlotRepository.getLive(value)

            spellLevels = getSpellLevelUseCase(viewModelScope, value)

            viewModelScope.launch {
                spellLevels.collect {
                    updateSpellFilters()
                }
            }

            updateSpellList()
        }

    fun setEditMode(editMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _editMode.emit(editMode)
        }
    }

    fun setSpellLevel(spellLevel: Int) {
        _spellLevel.value = spellLevel
    }

    private fun updateSpellList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (editMode.value) _editSpells.emit(spellRepository.get(characterID, spellLevel.value))
        }
    }

    private fun updateSpellFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            // Normal mode
            if (this@SpellsViewModel::spellLevels.isInitialized) {
                var levels = spellLevels.value.map { it.copy() }
                levels.forEach { it.filterSpells(search, classFilter) }
                levels = levels.filter { it.spells.isNotEmpty() }
                _filteredLevels.emit(levels)
            }

            // Edit mode
            val spells = editSpells.value.filter { spell ->
                (search.isEmpty() || spell.name.contains(search, true)) &&
                        (classFilter.isEmpty() || spell.classes.any { clazz -> classFilter.contains(clazz.name) })
            }
            _filteredEditSpells.emit(spells)
        }
    }

    fun updateCharacterSpell(characterSpell: CharacterSpell) {
        viewModelScope.launch(Dispatchers.IO) {
            characterSpellRepository.update(characterSpell)
        }
    }

    fun updateSpellSlot(spellSlot: SpellSlot) {
        viewModelScope.launch(Dispatchers.IO) {
            spellSlotRepository.save(spellSlot)
        }
    }

    fun refresh() = updateSpellList()
}