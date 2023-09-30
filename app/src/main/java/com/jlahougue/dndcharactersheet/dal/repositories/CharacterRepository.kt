package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.firebase.dao.CharacterDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class CharacterRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).characterDao()
    private val firebaseDao = CharacterDao()

    fun create() {
        insert(Character())
    }

    fun insert(character: Character): Long {
        val characterID = roomDao.insert(character)
        character.id = characterID
        firebaseDao.save(character)
        return characterID
    }

    fun saveToLocal(character: Character) = roomDao.insert(character)

    fun update(character: Character) {
        roomDao.update(character)
        firebaseDao.save(character)
    }

    fun delete(characterID: Long) {
        roomDao.delete(characterID)
        firebaseDao.delete(characterID)
    }

    fun get() = roomDao.get()

    fun get(characterID: Long) = roomDao.get(characterID)

    fun getIDs() = roomDao.getIDs()

    fun exists() = roomDao.exists()

    fun loadImage(characterID: Long, context: Context, view: ImageView) {
        firebaseDao.loadImage(characterID, context, view)
    }

    fun getFavoriteCharacter(callback: (Long?) -> Unit) {
        if (roomDao.hasFavorite())
            callback(roomDao.getFavorite())
        else callback(null)
    }

    fun updateFavoriteCharacter(characterID: Long, isFavorite: Boolean) {
        roomDao.updateFavorite(characterID, isFavorite)
        firebaseDao.updateFavorite(roomDao.getFavoriteMap())
    }

    fun uploadImage(characterID: Long, uri: Uri) = firebaseDao.uploadImage(characterID, uri)
}