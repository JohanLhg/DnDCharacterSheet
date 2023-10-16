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

    fun fetchAll(setProgress: (Int, Int) -> Unit, callback: () -> Unit) {
        val names = roomDao.getNames()
        apiDao.fetchClasses(names, this::saveClass, this::fetchLevels, setProgress, callback)
    }

    private fun fetchLevels(clazz: String) {
        apiLevelDao.fetchClassLevels(clazz, this::saveClassLevel, this::saveClassSpellSlot)
    }

    private fun saveClass(clazz: Class) = roomDao.insert(clazz)

    private fun saveClassLevel(classLevel: ClassLevel) = roomLevelDao.insert(classLevel)

    private fun saveClassSpellSlot(classSpellSlot: ClassSpellSlot) = roomSpellSlotDao.insert(classSpellSlot)
}