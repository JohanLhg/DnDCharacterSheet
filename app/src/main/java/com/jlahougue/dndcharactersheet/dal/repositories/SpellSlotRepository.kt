package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellSlotRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellSlotDao()
    private val firebaseDao = com.jlahougue.dndcharactersheet.dal.firebase.dao.SpellSlotDao()

    fun create(characterID: Long) {
        val spellSlots = mutableListOf<SpellSlot>()
        for (level in 0..9) {
            spellSlots.add(SpellSlot(characterID, level, 0))
        }
        roomDao.insertAll(spellSlots)
        firebaseDao.insertAll(spellSlots)
    }

    fun save(spellSlot: SpellSlot) {
        roomDao.insert(spellSlot)
        firebaseDao.insert(spellSlot)
    }

    fun save(spellSlots: List<SpellSlot>) {
        roomDao.insertAll(spellSlots)
        firebaseDao.insertAll(spellSlots)
    }

    fun saveToLocal(spellSlot: SpellSlot) = roomDao.insert(spellSlot)

    fun deleteForCharacter(characterID: Long) = roomDao.deleteForCharacter(characterID)

    fun getLive(characterID: Long) = roomDao.getLive(characterID)

    fun getFlow(characterID: Long) = roomDao.getFlow(characterID)

    fun getMap(characterID: Long): Map<String, Int> {
        val map = roomDao.getMap(characterID).mapKeys { it.key.toString() }
        println(map)
        return map
    }

    fun restoreSpellSlots(characterID: Long) = create(characterID)
}