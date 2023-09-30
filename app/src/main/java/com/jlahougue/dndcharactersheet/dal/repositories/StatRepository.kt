package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Stat
import com.jlahougue.dndcharactersheet.dal.firebase.dao.StatDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class StatRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).statDao()
    private val firebaseDao = StatDao()

    fun create(characterID: Long) {
        insert(Stat(characterID, ARMOR_CLASS, 0))
        insert(Stat(characterID, SPEED, 0))
    }

    fun insert(stat: Stat) {
        roomDao.insert(stat)
        firebaseDao.insert(stat)
    }

    fun update(stat: Stat) {
        roomDao.update(stat)
        firebaseDao.update(stat)
    }

    companion object {
        const val ARMOR_CLASS = "AC"
        const val SPEED = "SPEED"
    }
}