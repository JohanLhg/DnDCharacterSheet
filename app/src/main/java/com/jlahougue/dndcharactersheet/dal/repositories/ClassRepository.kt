package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.dndAPI.dao.ClassLevelDao
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.dal.open5eAPI.dao.ClassDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class ClassRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).classDao()
    private val roomLevelDao = DnDDatabase.getInstance(application).classLevelDao()
    private val roomSpellSlotDao = DnDDatabase.getInstance(application).classSpellSlotDao()
    private val apiDao = ClassDao()
    private val apiLevelDao = ClassLevelDao()

    fun fetchAll(
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val names = roomDao.getNames()
        apiDao.fetchClasses(
            names,
            this::save,
            this::fetchLevels,
            progressKey,
            setProgressMax,
            updateProgress
        )
    }

    private fun fetchLevels(clazz: String) {
        apiLevelDao.fetchClassLevels(clazz, this::saveLevel, this::saveSpellSlot)
    }

    private fun save(clazz: Class) = roomDao.insert(clazz)

    private fun saveLevel(classLevel: ClassLevel) = roomLevelDao.insert(classLevel)

    private fun saveSpellSlot(classSpellSlot: ClassSpellSlot) = roomSpellSlotDao.insert(classSpellSlot)

    fun getNames() = roomDao.getNames()
}