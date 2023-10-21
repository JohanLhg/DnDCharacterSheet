package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.open5eAPI.dao.SpellDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellDao()
    private var roomClassDao = DnDDatabase.getInstance(application).spellClassDao()
    private var roomDamageDao = DnDDatabase.getInstance(application).spellDamageDao()
    private val apiDao = SpellDao()

    fun fetchAll(
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val names = roomDao.getNames()
        apiDao.fetchSpells(
            names,
            this::saveSpell,
            this::saveSpellClass,
            progressKey,
            setProgressMax,
            updateProgress
        )
    }

    private fun saveSpell(spell: Spell) = roomDao.insert(spell)

    private fun saveSpellClass(spellClass: SpellClass) = roomClassDao.insert(spellClass)

    private fun saveSpellDamage(spellDamage: SpellDamage) = roomDamageDao.insert(spellDamage)

    fun get(characterID: Long): Map<Int, List<SpellWithCharacterInfo>> {
        val spells = roomDao.get(characterID)
        val map = mutableMapOf<Int, MutableList<SpellWithCharacterInfo>>()
        spells.forEach {
            val level = it.level
            if (map[level] == null) map[level] = mutableListOf()
            map[level]!!.add(it)
        }
        return map
    }

    fun get(characterID: Long, spellLevel: Int) = roomDao.get(characterID, spellLevel)

    fun getUnlocked(characterID: Long): Map<Int, List<SpellWithCharacterInfo>> {
        val spells = roomDao.getUnlocked(characterID)
        val map = mutableMapOf<Int, MutableList<SpellWithCharacterInfo>>()
        spells.forEach {
            val level = it.level
            if (map[level] == null) map[level] = mutableListOf()
            map[level]!!.add(it)
        }
        return map
    }

    fun getUnlocked(characterID: Long, spellLevel: Int) = roomDao.getUnlocked(characterID, spellLevel)
}