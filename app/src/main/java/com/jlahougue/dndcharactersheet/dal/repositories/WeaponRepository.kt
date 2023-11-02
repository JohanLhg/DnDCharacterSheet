package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao.WeaponDao
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeaponRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).weaponDao()
    private val apiDao = WeaponDao()

    fun fetchAll(
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Weapon, List<WeaponProperty>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val names = roomDao.getNames()
            apiDao.fetchWeapons(
                names,
                cancel,
                setProgressMax,
                skip,
                save
            )
        }
    }

    fun save(weapon: Weapon) = roomDao.insert(weapon)
}