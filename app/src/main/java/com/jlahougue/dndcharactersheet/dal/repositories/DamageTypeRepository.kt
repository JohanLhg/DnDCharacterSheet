package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao.DamageTypeDao
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DamageTypeRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).damageTypeDao()
    private val apiDao = DamageTypeDao()

    fun fetchAll(
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (DamageType) -> Unit
    ) {
         CoroutineScope(Dispatchers.IO).launch {
            val names = roomDao.getNames()
            apiDao.fetchDamageTypes(
                names,
                cancel,
                setProgressMax,
                skip,
                save
            )
        }
    }

    suspend fun save(damageType: DamageType) {
        withContext(Dispatchers.IO) {
            roomDao.insert(damageType)
        }
    }

    suspend fun getNames(): List<String> {
        return withContext(Dispatchers.IO) {
            roomDao.getNames()
        }
    }
}