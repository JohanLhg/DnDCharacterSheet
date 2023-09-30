package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Quests
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class QuestsDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(quests: Quests) {
        firebaseDatabase.updateCharacterSheet(quests.cid, mapOf("quests" to quests.content))
    }
}