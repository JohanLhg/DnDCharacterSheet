package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellSlotRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellSlotDao()

    fun create(characterID: Long) {
        val spellSlots = mutableListOf<SpellSlot>()
        for (level in 1..9) {
            spellSlots.add(SpellSlot(characterID, level, 0))
        }
        roomDao.insertAll(spellSlots)
    }

    fun saveToLocal(spellSlot: SpellSlot) = roomDao.insert(spellSlot)

    fun getMap(characterID: Long) = roomDao.getMap(characterID)
}