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
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellSlotRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellcastingRepository
import kotlin.concurrent.thread

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository = CharacterRepository(application)
    private val characterSpellRepository = CharacterSpellRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellSlotRepository = SpellSlotRepository(application)
    private val spellRepository = SpellRepository(application)

    lateinit var spellcasting: LiveData<SpellcastingView>
    lateinit var characterSpellStats: LiveData<CharacterSpellStatsView>
    val classes = MutableLiveData<List<String>>(null)
    val editMode = MutableLiveData(false)
    val spellLevel = MutableLiveData(0)
    lateinit var spellLevels: LiveData<List<SpellSlotView>>
    val spells = MutableLiveData<List<SpellWithCharacterInfo>>(null)

    var characterID = 0L
        set(value) {
            field = value
            spellcasting = spellcastingRepository.get(value)
            characterSpellStats = characterSpellRepository.getCharacterSpellStats(value)
            spellLevels = spellSlotRepository.get(value)
            updateSpellList()
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
        }
    }

    fun updateCharacterSpell(characterSpell: CharacterSpell) {
        thread {
            characterSpellRepository.update(characterSpell)
        }
    }

    fun refresh() = setEditMode(editMode.value!!)
}