package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class SpellSlotDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun insert(spellSlot: SpellSlot) {
        firebaseDatabase.updateCharacterSheet(spellSlot.cid, mapOf("spellSlots.${spellSlot.level}" to spellSlot.used))
    }

    fun insertAll(spellSlots: List<SpellSlot>) {
        val characterID = spellSlots[0].cid
        val map = mutableMapOf<String, Int>()
        for (spellSlot in spellSlots) {
            map[spellSlot.level.toString()] = spellSlot.used
        }
        firebaseDatabase.updateCharacterSheet(characterID, mapOf("spellSlots" to map))
    }
}