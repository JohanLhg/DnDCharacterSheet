package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao.DamageTypeDao
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class DamageTypeRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).damageTypeDao()
    private val apiDao = DamageTypeDao()

    suspend fun fetchAll(
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        save: (DamageType) -> Unit
    ) {
        val names = getNames()
        apiDao.fetchDamageTypes(
            names,
            cancel,
            setProgressMax,
            skip,
            updateProgress,
            save
        )
    }

    fun save(damageType: DamageType) = roomDao.insert(damageType)

    fun getNames() = roomDao.getNames()
}