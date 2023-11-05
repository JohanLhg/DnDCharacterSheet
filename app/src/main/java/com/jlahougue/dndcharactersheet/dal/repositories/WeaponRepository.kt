package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao.WeaponDao
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class WeaponRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).weaponDao()
    private val apiDao = WeaponDao()

    suspend fun fetchAll(
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        saveWeapon: (Weapon) -> Long,
        saveProperties: (List<WeaponProperty>) -> Unit
    ) {
        val names = roomDao.getNames()
        apiDao.fetchWeapons(
            names,
            cancel,
            setProgressMax,
            skip,
            updateProgress,
            saveWeapon,
            saveProperties
        )
    }

    fun save(weapon: Weapon) = roomDao.insert(weapon)
}