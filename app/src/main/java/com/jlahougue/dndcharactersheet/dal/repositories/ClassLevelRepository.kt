package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao.ClassLevelDao
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassLevelRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).classLevelDao()
    private val apiDao = ClassLevelDao()

    fun fetchLevelsForClass(
        clazz: String,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        save: (ClassLevel, List<ClassSpellSlot>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            apiDao.fetchClassLevels(
                clazz,
                cancel,
                setProgressMax,
                save
            )
        }
    }

    fun save(classLevel: ClassLevel) = roomDao.insert(classLevel)
}