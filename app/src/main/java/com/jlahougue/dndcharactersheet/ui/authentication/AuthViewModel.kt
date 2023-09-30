package com.jlahougue.dndcharactersheet.ui.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSheetRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository
import kotlin.concurrent.thread

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesRepository = PreferencesRepository(application)
    private val authRepository = AuthRepository()
    private val characterSheetRepository = CharacterSheetRepository(application)

    var authMode = MutableLiveData(REGISTER)

    val waitingFor = MutableLiveData(listOf(SEARCHING_FOR_CHARACTER))

    fun isLoggedIn() = authRepository.isLoggedIn()

    fun register(email: String, password: String, callback: (Boolean) -> Unit) {
        authRepository.register(email, password, callback)
    }

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        authRepository.login(email, password, callback)
    }

    fun load() {
        thread {
            characterSheetRepository.createCharacterIfNotExists {
                waitingFor.postValue(waitingFor.value!!.filter { it != SEARCHING_FOR_CHARACTER })
            }
        }
    }

    fun getLanguage(callback: (String) -> Unit) {
        thread { preferencesRepository.getLanguage(callback) }
    }

    companion object {
        const val REGISTER = 0
        const val LOGIN = 1

        const val SEARCHING_FOR_CHARACTER = 0

        const val LANGUAGE_LOADED = "LANGUAGE_LOADED"
    }
}