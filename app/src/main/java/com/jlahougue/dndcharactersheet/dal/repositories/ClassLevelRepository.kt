package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao.ClassLevelDao
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class ClassLevelRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).classLevelDao()
    private val apiDao = ClassLevelDao()

    suspend fun fetchLevelsForClass(
        clazz: String,
        saveLevel: (ClassLevel) -> Long,
        saveSpellSlots: (List<ClassSpellSlot>) -> Unit
    ) {
        apiDao.fetchClassLevels(
            clazz,
            saveLevel,
            saveSpellSlots
        )
    }

    fun save(classLevel: ClassLevel) = roomDao.insert(classLevel)
}