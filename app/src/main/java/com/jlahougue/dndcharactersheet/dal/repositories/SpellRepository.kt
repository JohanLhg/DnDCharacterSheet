package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.dndAPI.dao.SpellDao
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellDao()
    private var roomClassDao = DnDDatabase.getInstance(application).spellClassDao()
    private var roomDamageDao = DnDDatabase.getInstance(application).spellDamageDao()
    private val apiDao = SpellDao()

    fun fetchAll(callback: () -> Unit) {
        val names = roomDao.getNames()
        apiDao.fetchSpells(names, this::saveSpell, this::saveSpellClass, this::saveSpellDamage, callback)
    }

    private fun saveSpell(spell: Spell) = roomDao.insert(spell)

    private fun saveSpellClass(spellClass: SpellClass) = roomClassDao.insert(spellClass)

    private fun saveSpellDamage(spellDamage: SpellDamage) = roomDamageDao.insert(spellDamage)
}