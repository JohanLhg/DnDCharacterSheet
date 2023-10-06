package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.firebase.dao.CharacterSpellDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class CharacterSpellRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).characterSpellDao()
    private val firebaseDao = CharacterSpellDao()

    fun insert(characterID: Long, spellName: String) {
        val characterSpell = CharacterSpell(cid = characterID, name = spellName)
        roomDao.insert(characterSpell)
        firebaseDao.insert(characterSpell)
    }
}