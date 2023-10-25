package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlin.concurrent.thread

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val classRepository = ClassRepository(application)
    private val characterSpellRepository = CharacterSpellRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellSlotRepository = SpellSlotRepository(application)
    private val spellRepository = SpellRepository(application)

    lateinit var spellcasting: LiveData<SpellcastingView>
    lateinit var characterSpellStats: LiveData<CharacterSpellStatsView>
    val classes = MutableLiveData<List<String>>(listOf())
    val spellLevel = MutableLiveData(0)
    lateinit var spellLevels: LiveData<List<SpellSlotView>>
    val spells = MutableLiveData<List<SpellWithCharacterInfo>>(listOf())
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
        thread {
            classes.postValue(classRepository.getNames())
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
        if (spellLevel == this.spellLevel.value) return
        this.spellLevel.postValue(spellLevel)
        updateSpellList()
    }

    private fun updateSpellList() {
        thread {
            if (editMode.value!!) spells.postValue(spellRepository.get(characterID, spellLevel.value!!))
            else spells.postValue(spellRepository.getUnlocked(characterID, spellLevel.value!!))
            updateSpellFilters()
        }
    }

    fun updateSpellFilters() {
        thread {
            if (classFilter.isEmpty()) {
                filteredSpells.postValue(spells.value!!.filter { spell ->
                    spell.name.contains(search, true)
                })
                return@thread
            }
            filteredSpells.postValue(spells.value!!.filter { spell ->
                spell.classes.any { clazz -> classFilter.contains(clazz.name) }
                        && spell.name.contains(search, true)
            })
        }
    }

    fun updateCharacterSpell(characterSpell: CharacterSpell) {
        thread {
            characterSpellRepository.update(characterSpell)
        }
    }

    fun refresh() = setEditMode(editMode.value!!)
}