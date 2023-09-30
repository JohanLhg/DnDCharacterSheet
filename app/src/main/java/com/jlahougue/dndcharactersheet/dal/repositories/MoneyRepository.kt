package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Money
import com.jlahougue.dndcharactersheet.dal.firebase.dao.MoneyDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class MoneyRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).moneyDao()
    private val firebaseDao = MoneyDao()

    fun create(characterID: Long) {
        insert(Money(characterID))
    }

    fun insert(money: Money) {
        roomDao.insert(money)
        firebaseDao.insert(money)
    }

    fun update(money: Money) {
        roomDao.update(money)
        firebaseDao.update(money)
    }

    fun delete(money: Money) {
        roomDao.delete(money)
        firebaseDao.delete(money)
    }
}