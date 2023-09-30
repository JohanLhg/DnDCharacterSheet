package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Quests
import com.jlahougue.dndcharactersheet.dal.firebase.dao.QuestsDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class QuestsRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).questsDao()
    private val firebaseDao = QuestsDao()

    fun create(characterID: Long) {
        insert(Quests(characterID))
    }

    fun insert(quests: Quests) {
        roomDao.insert(quests)
        firebaseDao.insert(quests)
    }

    fun update(quests: Quests) {
        roomDao.update(quests)
        firebaseDao.update(quests)
    }

    fun delete(quests: Quests) {
        roomDao.delete(quests)
        firebaseDao.delete(quests)
    }
}