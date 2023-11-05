package com.jlahougue.dndcharactersheet.ui.main

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.CharacterInfo
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSheetRepository
import com.jlahougue.dndcharactersheet.dal.repositories.ClassRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val characterSheetRepository = CharacterSheetRepository(application)
    private val characterRepository = CharacterRepository(application)
    private val preferencesRepository = PreferencesRepository(application)
    private val classRepository = ClassRepository(application)

    private val authRepository = AuthRepository()

    val preferences = preferencesRepository.get()

    val character = MutableLiveData<CharacterInfo>(null)

    var characterID = MutableLiveData(-1L)

    fun setCharacterId(id: Long) {
        if (id == -1L) return
        viewModelScope.launch(Dispatchers.IO) {
            characterID.postValue(id)
            character.postValue(characterRepository.getInfo(id))
        }
    }

    fun updateCharacter(character: CharacterInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            this@MainViewModel.character.postValue(character)
            characterRepository.update(character.getCharacter())
        }
    }
    
    fun getClasses(callback: (List<Class>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { callback(classRepository.get()) }
    }

    fun loadCharacterImage(context: Context, imageProfile: ImageView) {
        viewModelScope.launch(Dispatchers.IO) { characterRepository.loadImage(characterID.value!!, context, imageProfile) }
    }

    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) { characterRepository.uploadImage(characterID.value!!, uri) }
    }

    fun updatePreferences(language: String = "", unitSystem: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val pref = preferences.value!!
            if (language != "") pref.language = language
            if (unitSystem != "") pref.unitSystem = unitSystem
            preferencesRepository.update(pref)
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

    fun saveToRemote() {
        viewModelScope.launch(Dispatchers.IO) { characterSheetRepository.saveToRemote(characterID.value!!) }
    }

    companion object {
        const val CHARACTER_ID = "CID"
    }
}