package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class CharacterWeaponDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun insert(characterWeapon: CharacterWeapon) {
        firebaseDatabase.updateCharacterSheet(
            characterWeapon.cid,
            mapOf("weapons.${characterWeapon.name}" to characterWeapon.count)
        )
    }
}