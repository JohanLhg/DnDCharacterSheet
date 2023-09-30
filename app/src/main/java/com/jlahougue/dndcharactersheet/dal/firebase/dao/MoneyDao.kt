package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Money
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class MoneyDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(money: Money) {
        firebaseDatabase.updateCharacterSheet(money.cid, mapOf("money" to money))
    }
}