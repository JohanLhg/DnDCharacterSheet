package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class CharacterSpellDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun insert(characterSpell: CharacterSpell) {
        firebaseDatabase.updateCharacterSheet(characterSpell.cid, mapOf("spells.${characterSpell.sid}" to characterSpell))
    }
}