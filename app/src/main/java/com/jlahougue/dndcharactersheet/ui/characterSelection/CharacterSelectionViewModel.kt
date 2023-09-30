package com.jlahougue.dndcharactersheet.ui.characterSelection

import android.app.Application
import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSheetRepository
import kotlin.concurrent.thread

class CharacterSelectionViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val characterRepository = CharacterRepository(application)
    private val characterSheetRepository = CharacterSheetRepository(application)

    val characters = characterRepository.get()

    companion object {
        const val AUTO_LOAD = "AUTO_LOAD"
    }

    fun createCharacter(callback: (Long) -> Unit) {
        thread {
            val characterID = characterSheetRepository.create()
            callback(characterID)
        }
    }

    fun loadCharacterImage(characterID: Long, context: Context, imageProfile: ImageView) {
        thread { characterRepository.loadImage(characterID, context, imageProfile) }
    }

    fun deleteCharacter(characterID: Long) {
        thread { characterRepository.delete(characterID) }
    }

    fun signOut() {
        authRepository.signOut()
    }

    fun getFavoriteCharacter(callback: (Long?) -> Unit) {
        thread { characterRepository.getFavoriteCharacter(callback) }
    }

    fun updateFavoriteCharacter(characterID: Long, isFavorite: Boolean) {
        thread {
            characterRepository.updateFavoriteCharacter(characterID, isFavorite)
        }
    }
}