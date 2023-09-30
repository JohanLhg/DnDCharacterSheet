package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Stats
import com.jlahougue.dndcharactersheet.dal.firebase.dao.StatsDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class StatsRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).statDao()
    private val firebaseDao = StatsDao()

    fun create(characterID: Long) {
        insert(Stats(characterID))
    }

    fun insert(stats: Stats) {
        roomDao.insert(stats)
        firebaseDao.save(stats)
    }

    fun saveToLocal(stats: Stats) = roomDao.insert(stats)

    fun update(stats: Stats) {
        roomDao.update(stats)
        firebaseDao.save(stats)
    }

    fun get(characterID: Long) = roomDao.get(characterID)
}