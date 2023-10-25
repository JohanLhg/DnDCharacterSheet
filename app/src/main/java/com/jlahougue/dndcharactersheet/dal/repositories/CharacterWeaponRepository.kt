package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.firebase.dao.CharacterWeaponDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class CharacterWeaponRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).characterWeaponDao()
    private val firebaseDao = CharacterWeaponDao()

    fun insert(characterWeapon: CharacterWeapon) {
        roomDao.insert(characterWeapon)
        firebaseDao.insert(characterWeapon)
    }

    fun saveToLocal(characterWeapon: CharacterWeapon) = roomDao.insert(characterWeapon)

    fun get(characterID: Long) = roomDao.get(characterID)

    fun getWeapon(characterID: Long, name: String) = roomDao.getWeapon(characterID, name)

    fun getNotOwned(characterID: Long) = roomDao.getNotOwned(characterID)

    fun getMap(characterID: Long) = roomDao.getMap(characterID)

    fun addWeapons(characterID: Long, weaponCounts: Map<String, Int>) {
        val weapons = listOf<CharacterWeapon>()
        for ((weaponName, count) in weaponCounts) {
            weapons.plus(CharacterWeapon(characterID, weaponName, count))
        }
        roomDao.insert(weapons)
    }
}