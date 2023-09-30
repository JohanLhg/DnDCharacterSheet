package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves
import com.jlahougue.dndcharactersheet.dal.firebase.dao.DeathSavesDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class DeathSavesRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).deathSavesDao()
    private val firebaseDao = DeathSavesDao()

    fun create(characterID: Long) {
        insert(DeathSaves(characterID))
    }

    fun insert(deathSaves: DeathSaves) {
        roomDao.insert(deathSaves)
        firebaseDao.insert(deathSaves)
    }

    fun update(deathSaves: DeathSaves) {
        roomDao.update(deathSaves)
        firebaseDao.update(deathSaves)
    }

    fun delete(deathSaves: DeathSaves) {
        roomDao.delete(deathSaves)
        firebaseDao.delete(deathSaves)
    }
}