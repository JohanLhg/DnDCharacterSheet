package com.jlahougue.dndcharactersheet.ui.main

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSheetRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository
import kotlin.concurrent.thread

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val characterSheetRepository = CharacterSheetRepository(application)
    private val characterRepository = CharacterRepository(application)
    private val preferencesRepository = PreferencesRepository(application)

    private val authRepository = AuthRepository()

    val character = MutableLiveData<Character>(null)

    var characterID = MutableLiveData(-1L)

    fun setCharacterId(id: Long) {
        if (id == -1L) return
        thread {
            characterID.postValue(id)
            character.postValue(characterRepository.get(id))
        }
    }

    fun updateCharacter(character: Character) {
        thread { characterRepository.update(character) }
    }

    fun loadCharacterImage(context: Context, imageProfile: ImageView) {
        thread { characterRepository.loadImage(characterID.value!!, context, imageProfile) }
    }

    fun updateProfileImage(uri: Uri) {
        thread { characterRepository.uploadImage(characterID.value!!, uri) }
    }

    fun setLanguage(language: String) {
        thread {
            preferencesRepository.setLanguage(language)
        }
    }

    fun changeEmail(email: String, callback: (Boolean) -> Unit) {
        authRepository.changeEmail(email, callback)
    }

    fun changePassword(password: String, callback: (Boolean) -> Unit) {
        authRepository.changePassword(password, callback)
    }

    fun signOut() {
        authRepository.signOut()
    }

    fun getLanguage(callback: (String) -> Unit) {
        thread { preferencesRepository.getLanguage(callback) }
    }

    fun saveToRemote() {
        thread { characterSheetRepository.saveToRemote(characterID.value!!) }
    }

    companion object {
        const val CHARACTER_ID = "CID"
    }
}