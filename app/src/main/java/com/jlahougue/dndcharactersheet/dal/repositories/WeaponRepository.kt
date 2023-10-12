package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.dndAPI.dao.WeaponDao
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class WeaponRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).weaponDao()
    private val roomPropertyDao = DnDDatabase.getInstance(application).weaponPropertyDao()
    private val apiDao = WeaponDao()

    fun fetchAll(setProgress: (Int, Int) -> Unit, callback: () -> Unit) {
        val names = roomDao.getNames()
        apiDao.fetchWeapons(names, this::saveWeapon, this::saveProperty, setProgress, callback)
    }

    private fun saveWeapon(weapon: Weapon) = roomDao.insert(weapon)

    private fun saveProperty(property: WeaponProperty) = roomPropertyDao.insert(property)

    //fun get(characterID: Long) = roomDao.get(characterID)
}