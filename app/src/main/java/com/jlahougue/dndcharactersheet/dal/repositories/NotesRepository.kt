package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Notes
import com.jlahougue.dndcharactersheet.dal.firebase.dao.NotesDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class NotesRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).notesDao()
    private val firebaseDao = NotesDao()

    fun create(characterID: Long) {
        insert(Notes(characterID))
    }

    fun insert(notes: Notes) {
        roomDao.insert(notes)
        firebaseDao.save(notes)
    }

    fun saveToLocal(notes: Notes) = roomDao.insert(notes)

    fun update(notes: Notes) {
        roomDao.update(notes)
        firebaseDao.save(notes)
    }

    fun get(characterID: Long) = roomDao.get(characterID)
}