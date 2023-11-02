package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.dao.ClassDao
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).classDao()
    private val apiDao = ClassDao()

    fun fetchAll(
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Class) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val names = roomDao.getNames()
            apiDao.fetchClasses(
                names,
                cancel,
                setProgressMax,
                skip,
                save
            )
        }
    }

    fun save(clazz: Class) = roomDao.insert(clazz)

    fun getNames() = roomDao.getNames()
}