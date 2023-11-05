package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.dao.ClassDao
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase
import kotlin.reflect.KSuspendFunction1

class ClassRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).classDao()
    private val apiDao = ClassDao()

    suspend fun fetchAll(
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Class) -> Long,
        fetchClassLevels: KSuspendFunction1<String, Unit>
    ) {
        val names = roomDao.getNames()
        apiDao.fetchClasses(
            names,
            cancel,
            setProgressMax,
            skip,
            save,
            fetchClassLevels
        )
    }

    fun save(clazz: Class) = roomDao.insert(clazz)

    fun getNames() = roomDao.getNames()

    fun get() = roomDao.get()
}