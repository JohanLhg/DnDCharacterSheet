package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellcastingRepository
import com.jlahougue.dndcharactersheet.dal.room.views.SpellcastingView
import kotlin.concurrent.thread

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository = CharacterRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellRepository = SpellRepository(application)

    val characterLevel = MutableLiveData<Int>(null)
    lateinit var spellcasting: LiveData<SpellcastingView>
    val spells = MutableLiveData<Map<Int, List<SpellWithCharacterInfo>>>(null)

    var characterID = 0L
        set(value) {
            field = value
            spellcasting = spellcastingRepository.get(value)
            thread {
                characterLevel.postValue(characterRepository.getLevel(value))
                spells.postValue(spellRepository.getUnlocked(value))
            }
        }

    fun setEditMode(editMode: Boolean) {
        thread {
            if (editMode) spells.postValue(spellRepository.get(characterID))
            else spells.postValue(spellRepository.getUnlocked(characterID))
        }
    }
}