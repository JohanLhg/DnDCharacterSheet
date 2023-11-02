package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellDamageRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellDamageDao()

    fun save(spellDamages: List<SpellDamage>) = roomDao.insert(spellDamages)
}