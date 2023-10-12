package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.entities.views.CharacterSpellStatsView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellcastingRepository
import kotlin.concurrent.thread

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository = CharacterRepository(application)
    private val characterSpellRepository = CharacterSpellRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellRepository = SpellRepository(application)

    val characterLevel = MutableLiveData<Int>(null)
    lateinit var spellcasting: LiveData<SpellcastingView>
    lateinit var characterSpellStats: LiveData<CharacterSpellStatsView>
    val editMode = MutableLiveData(false)
    val spells = MutableLiveData<Map<Int, List<SpellWithCharacterInfo>>>(null)

    var characterID = 0L
        set(value) {
            field = value
            spellcasting = spellcastingRepository.get(value)
            characterSpellStats = characterSpellRepository.getCharacterSpellStats(value)
            thread {
                characterLevel.postValue(characterRepository.getLevel(value))
                spells.postValue(spellRepository.getUnlocked(value))
            }
        }

    fun setEditMode(editMode: Boolean) {
        this.editMode.postValue(editMode)
        thread {
            if (editMode) spells.postValue(spellRepository.get(characterID))
            else spells.postValue(spellRepository.getUnlocked(characterID))
        }
    }

    fun updateCharacterSpell(characterSpell: CharacterSpell) {
        thread {
            characterSpellRepository.update(characterSpell)
        }
    }

    fun refresh() = setEditMode(editMode.value!!)
}