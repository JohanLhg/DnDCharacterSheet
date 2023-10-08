package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Spellcasting
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class SpellcastingDao {
    val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(spellcasting: Spellcasting) {
        firebaseDatabase.updateCharacterSheet(spellcasting.cid, mapOf("spellcasting" to spellcasting))
    }
}