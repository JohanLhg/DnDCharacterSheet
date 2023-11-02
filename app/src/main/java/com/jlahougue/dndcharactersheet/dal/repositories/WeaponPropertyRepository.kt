package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class WeaponPropertyRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).weaponPropertyDao()

    fun save(weaponProperties: List<WeaponProperty>) = roomDao.insert(weaponProperties)
}