package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.firebase.dao.CharacterDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class CharacterRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).characterDao()
    private val firebaseDao = CharacterDao()

    fun create() {
        insert(Character())
    }

    fun insert(character: Character) {
        roomDao.insert(character)
        firebaseDao.insert(character)
    }

    fun update(character: Character) {
        roomDao.update(character)
        firebaseDao.update(character)
    }

    fun delete(character: Character) {
        roomDao.delete(character)
        firebaseDao.delete(character)
    }
}