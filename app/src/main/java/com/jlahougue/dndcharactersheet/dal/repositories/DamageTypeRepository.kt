package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.dndAPI.dao.DamageTypeDao
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class DamageTypeRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).damageTypeDao()
    private val apiDao = DamageTypeDao()

    fun fetchAll(
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val names = roomDao.getNames()
        apiDao.fetchDamageTypes(
            names,
            this::saveDamageType,
            progressKey,
            setProgressMax,
            updateProgress
        )
    }

    private fun saveDamageType(damageType: DamageType) = roomDao.insert(damageType)
}