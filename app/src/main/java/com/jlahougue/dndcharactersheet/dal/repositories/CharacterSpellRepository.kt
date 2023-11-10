package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.firebase.dao.CharacterSpellDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class CharacterSpellRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).characterSpellDao()
    private val firebaseDao = CharacterSpellDao()

    fun update(characterSpell: CharacterSpell) {
        if (!characterSpell.unlocked) {
            characterSpell.prepared = false
            characterSpell.alwaysPrepared = false
        }
        else characterSpell.highlighted = false
        if (characterSpell.prepared) characterSpell.alwaysPrepared = false
        if (characterSpell.alwaysPrepared) characterSpell.prepared = false
        roomDao.insert(characterSpell)
        firebaseDao.insert(characterSpell)
    }

    fun saveToLocal(spell: CharacterSpell) = roomDao.insert(spell)

    fun deleteForCharacter(characterID: Long) = roomDao.deleteForCharacter(characterID)

    fun getMap(characterID: Long): Map<String, CharacterSpell> = roomDao.getMap(characterID)

    fun getCharacterSpellStats(characterID: Long) = roomDao.getCharacterSpellStats(characterID)

    companion object {
        fun getSpellSlotsForLevel(characterLevel: Int, spellLevel: Int): Int {
            if (characterLevel < 1) return 0
            return when (spellLevel) {
                1 -> when (characterLevel) {
                    1 -> 2
                    2 -> 3
                    else -> 4
                }
                2 -> when {
                    characterLevel < 3 -> 0
                    characterLevel == 3 -> 2
                    else -> 3
                }
                3 -> when {
                    characterLevel < 5 -> 0
                    characterLevel == 5 -> 1
                    else -> 3
                }
                4 -> when {
                    characterLevel < 7 -> 0
                    characterLevel == 7 -> 1
                    characterLevel == 8 -> 2
                    else -> 3
                }
                5 -> when {
                    characterLevel < 9 -> 0
                    characterLevel == 9 -> 1
                    characterLevel < 18 -> 2
                    else -> 3
                }
                6 -> when {
                    characterLevel < 11 -> 0
                    characterLevel < 19 -> 1
                    else -> 2
                }
                7 -> when {
                    characterLevel < 13 -> 0
                    characterLevel < 20 -> 1
                    else -> 2
                }
                8 -> if (characterLevel < 15) 0 else 1
                9 -> if (characterLevel < 19) 0 else 1
                else -> -1
            }
        }

        fun getMaxSpellLevel(characterLevel: Int): Int {
            return when {
                characterLevel < 3 -> 1
                characterLevel < 5 -> 2
                characterLevel < 7 -> 3
                characterLevel < 9 -> 4
                characterLevel < 11 -> 5
                characterLevel < 13 -> 6
                characterLevel < 15 -> 7
                characterLevel < 17 -> 8
                else -> 9
            }
        }
    }
}