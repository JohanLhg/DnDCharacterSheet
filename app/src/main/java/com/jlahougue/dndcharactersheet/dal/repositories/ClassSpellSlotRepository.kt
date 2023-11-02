package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class ClassSpellSlotRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).classSpellSlotDao()

    fun save(classSpellSlots: List<ClassSpellSlot>) = roomDao.insert(classSpellSlots)
}