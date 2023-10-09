package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellcastingRepository
import com.jlahougue.dndcharactersheet.dal.room.views.SpellcastingView
import kotlin.concurrent.thread

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository = CharacterRepository(application)
    private val characterSpellRepository = CharacterSpellRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellRepository = SpellRepository(application)

    val characterLevel = MutableLiveData<Int>(null)
    lateinit var spellcasting: LiveData<SpellcastingView>
    val editMode = MutableLiveData(false)
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
        this.editMode.postValue(editMode)
        thread {
            if (editMode) spells.postValue(spellRepository.get(characterID))
            else spells.postValue(spellRepository.getUnlocked(characterID))
        }
    }

    fun setSpellPrepared(spell: SpellWithCharacterInfo, prepared: Boolean) {
        val characterSpell: CharacterSpell
        if (spell.characterSpell == null) {
            characterSpell = CharacterSpell(
                cid = characterID,
                name = spell.spell.name,
                prepared = prepared
            )
        } else {
            characterSpell = spell.characterSpell
            characterSpell.prepared = prepared
        }
        thread {
            characterSpellRepository.update(characterSpell)
        }
    }

    fun setSpellUnlocked(spell: SpellWithCharacterInfo, unlocked: Boolean) {
        val characterSpell: CharacterSpell
        if (spell.characterSpell == null) {
            characterSpell = CharacterSpell(
                cid = characterID,
                name = spell.spell.name,
                unlocked = unlocked
            )
        } else {
            characterSpell = spell.characterSpell
            characterSpell.unlocked = unlocked
        }
        if (!unlocked) characterSpell.prepared = false
        thread {
            characterSpellRepository.update(characterSpell)
        }
    }
}