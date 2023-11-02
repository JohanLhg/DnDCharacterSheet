package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.entities.views.CharacterSpellStatsView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.ClassRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellSlotRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellcastingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val classRepository = ClassRepository(application)
    private val characterSpellRepository = CharacterSpellRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellSlotRepository = SpellSlotRepository(application)
    private val spellRepository = SpellRepository(application)

    lateinit var spellcasting: LiveData<SpellcastingView>
    lateinit var characterSpellStats: LiveData<CharacterSpellStatsView>
    val classes = MutableLiveData<List<String>>(listOf())

    private val _spellLevel = MutableStateFlow(0)
    val spellLevel = _spellLevel.asStateFlow()

    lateinit var spellLevels: LiveData<List<SpellSlotView>>

    private val _spells = MutableStateFlow<List<SpellWithCharacterInfo>>(listOf())
    private val spells = _spells.asStateFlow()

    val filteredSpells = MutableLiveData<List<SpellWithCharacterInfo>>(listOf())

    val editMode = MutableLiveData(false)
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
            spells.collect {
                updateSpellFilters()
            }
        }
        viewModelScope.launch {
            spellLevel.collect {
                updateSpellList()
            }
        }
    }

    var characterID = 0L
        set(value) {
            field = value
            spellcasting = spellcastingRepository.get(value)
            characterSpellStats = characterSpellRepository.getCharacterSpellStats(value)
            spellLevels = spellSlotRepository.get(value)
            setEditMode(editMode.value!!)
        }

    fun setEditMode(editMode: Boolean) {
        this.editMode.postValue(editMode)
        updateSpellList()
    }

    fun setSpellLevel(spellLevel: Int) {
        _spellLevel.value = spellLevel
    }

    private fun updateSpellList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (editMode.value!!) _spells.emit(spellRepository.get(characterID, spellLevel.value))
            else _spells.emit(spellRepository.getUnlocked(characterID, spellLevel.value))
        }
    }

    private fun updateSpellFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            if (classFilter.isEmpty()) {
                filteredSpells.postValue(spells.value.filter { spell ->
                    spell.name.contains(search, true)
                })
                return@launch
            }
            filteredSpells.postValue(spells.value.filter { spell ->
                spell.classes.any { clazz -> classFilter.contains(clazz.name) }
                        && spell.name.contains(search, true)
            })
        }
    }

    fun updateCharacterSpell(characterSpell: CharacterSpell) {
        viewModelScope.launch(Dispatchers.IO) {
            characterSpellRepository.update(characterSpell)
        }
    }

    fun refresh() = setEditMode(editMode.value!!)
}