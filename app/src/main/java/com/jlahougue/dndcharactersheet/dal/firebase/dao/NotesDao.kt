package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Notes
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class NotesDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(notes: Notes) {
        firebaseDatabase.updateCharacterSheet(notes.cid, mapOf("notes" to notes.content))
    }
}