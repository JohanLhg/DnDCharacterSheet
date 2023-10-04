package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class AbilityDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(ability: Ability) {
        firebaseDatabase.updateCharacterSheet(ability.cid, mapOf("abilities.${ability.name}" to ability))
    }

    fun updateProficiency(characterID: Long, abilityName: String, proficiency: Boolean) {
        firebaseDatabase.updateCharacterSheet(characterID, mapOf("abilities.${abilityName}.proficiency" to proficiency))
    }
}