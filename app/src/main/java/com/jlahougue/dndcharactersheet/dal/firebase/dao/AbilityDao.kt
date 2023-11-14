package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class AbilityDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(ability: Ability) {
        firebaseDatabase.updateCharacterSheet(ability.cid, mapOf("abilities.${ability.name.name}" to ability))
    }

    fun updateValue(cid: Long, name: String, value: Int) {
        firebaseDatabase.updateCharacterSheet(cid, mapOf("abilities.${name}.value" to value))
    }

    fun updateProficiency(characterID: Long, name: String, proficiency: Boolean) {
        firebaseDatabase.updateCharacterSheet(characterID, mapOf("abilities.${name}.proficiency" to proficiency))
    }
}