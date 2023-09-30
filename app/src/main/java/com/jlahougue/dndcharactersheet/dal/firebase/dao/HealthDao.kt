package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class HealthDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(health: Health) {
        firebaseDatabase.updateCharacterSheet(health.cid, mapOf("health" to health))
    }
}