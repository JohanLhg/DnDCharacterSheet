package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellDao()
    private val firebaseDao = com.jlahougue.dndcharactersheet.dal.firebase.dao.SpellDao()
    private val apiDao = com.jlahougue.dndcharactersheet.dal.dndAPI.SpellDao()

    fun create(characterID: Long) {
    }

    fun saveNewSpell(characterID: Long, spell: Spell) {
        roomDao.insert(spell)
    }

    fun fetchAll() {
        apiDao.getSpells().forEach { spell ->
            roomDao.insert(spell)
        }
    }
}