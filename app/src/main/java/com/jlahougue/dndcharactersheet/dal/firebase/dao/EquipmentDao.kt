package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Equipment
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class EquipmentDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(equipment: Equipment) {
        firebaseDatabase.updateCharacterSheet(equipment.cid, mapOf("equipment" to equipment.content))
    }
}