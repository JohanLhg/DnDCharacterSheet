package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.dndAPI.dao.DamageTypeDao
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class DamageTypeRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).damageTypeDao()
    private val apiDao = DamageTypeDao()

    fun fetchAll(setProgress: (Int, Int) -> Unit, callback: () -> Unit) {
        val names = roomDao.getNames()
        apiDao.fetchDamageTypes(names, this::saveDamageType, setProgress, callback)
    }

    private fun saveDamageType(damageType: DamageType) = roomDao.insert(damageType)
}