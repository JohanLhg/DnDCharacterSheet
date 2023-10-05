package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.firebase.dao.HealthDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class HealthRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).healthDao()
    private val firebaseDao = HealthDao()

    fun create(characterID: Long) {
        insert(Health(characterID))
    }

    fun insert(health: Health) {
        roomDao.insert(health)
        firebaseDao.save(health)
    }

    fun saveToLocal(health: Health) = roomDao.insert(health)

    fun update(health: Health) {
        roomDao.update(health)
        firebaseDao.save(health)
    }

    fun get(characterID: Long) = roomDao.get(characterID)

    fun getHitDiceNbr(characterID: Long) = roomDao.getHitDiceNbr(characterID)
}