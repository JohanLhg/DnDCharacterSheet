package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellClassRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellClassDao()

    fun save(spellClasses: List<SpellClass>) = roomDao.insert(spellClasses)
}