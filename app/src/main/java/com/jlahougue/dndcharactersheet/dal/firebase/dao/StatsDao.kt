package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Stats
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class StatsDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(stats: Stats) {
        firebaseDatabase.updateCharacterSheet(stats.cid, mapOf("stats" to stats))
    }
}