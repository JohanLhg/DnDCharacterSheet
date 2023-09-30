package com.jlahougue.dndcharactersheet.dal.firebase.dao

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class CharacterDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(character: Character) {
        firebaseDatabase.updateCharacterSheet(character.id, mapOf("character" to character))
    }

    fun delete(characterID: Long) {
        firebaseDatabase.characterReference(characterID).delete()
        firebaseDatabase.deleteImage(characterID)
    }

    fun updateFavorite(favoriteMap: Map<Long, Boolean>) {
        favoriteMap.forEach { (characterID, isFavorite) ->
            firebaseDatabase.updateCharacterSheet(characterID, mapOf("character.favorite" to isFavorite))
        }
    }

    fun uploadImage(characterID: Long, uri: Uri) {
        val imageRef = firebaseDatabase.storage.reference
            .child("Images/Characters/${firebaseDatabase.uid}/$characterID.png")
        firebaseDatabase.uploadImage(imageRef, uri)
    }

    fun loadImage(characterID: Long, context: Context, view: ImageView) {
        firebaseDatabase.loadImage("Images/Characters/${firebaseDatabase.uid}/$characterID.png", context, view)
    }
}