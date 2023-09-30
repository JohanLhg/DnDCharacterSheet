package com.jlahougue.dndcharactersheet.dal.firebase.dao

import android.content.Context
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

    fun loadCharacterImage(characterID: Long, context: Context, view: ImageView) {
        firebaseDatabase.loadImage("Images/Characters/${firebaseDatabase.uid}/$characterID.png", context, view)
    }

    fun updateFavorite(favoriteMap: Map<Long, Boolean>) {
        // TODO
    }
}