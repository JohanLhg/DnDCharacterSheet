package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.firebase.dao.HealthDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase
import kotlin.math.max

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

    fun getLive(characterID: Long) = roomDao.getLive(characterID)

    fun get(characterID: Long) = roomDao.get(characterID)

    fun longRest(characterID: Long) {
        val health = roomDao.get(characterID)
        val maxHitDiceNbr = roomDao.getHitDiceNbrValue(characterID)
        health.currentHp = health.maxHp
        health.hitDiceUsed = max(health.hitDiceUsed - (maxHitDiceNbr/2), 0)
        update(health)
    }

    fun getHitDiceNbr(characterID: Long) = roomDao.getHitDiceNbr(characterID)
}