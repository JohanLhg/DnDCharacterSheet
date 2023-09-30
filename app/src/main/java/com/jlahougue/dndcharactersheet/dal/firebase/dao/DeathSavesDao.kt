package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class DeathSavesDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(deathSaves: DeathSaves) {
        firebaseDatabase.updateCharacterSheet(deathSaves.cid, mapOf("deathSaves" to deathSaves))
    }
}