package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.dao.SpellDao
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).spellDao()
    private val apiDao = SpellDao()

    suspend fun fetchAll(
        damageTypes: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        saveSpell: (Spell) -> Long,
        saveClasses: (List<SpellClass>) -> Unit,
        saveDamages: (List<SpellDamage>) -> Unit
    ) {
        val ids = roomDao.getIds()
        apiDao.fetchSpells(
            ids,
            damageTypes,
            cancel,
            setProgressMax,
            skip,
            updateProgress,
            saveSpell,
            saveClasses,
            saveDamages
        )
    }

    fun save(spell: Spell) = roomDao.insert(spell)

    fun getIds() = roomDao.getIds()

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

    fun getUnlockedOld(characterID: Long): Map<Int, List<SpellWithCharacterInfo>> {
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

    fun getUnlocked(characterID: Long) = roomDao.getUnlocked(characterID)
}