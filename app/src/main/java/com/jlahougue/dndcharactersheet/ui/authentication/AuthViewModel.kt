package com.jlahougue.dndcharactersheet.ui.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSheetRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import kotlin.concurrent.thread

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesRepository = PreferencesRepository(application)
    private val authRepository = AuthRepository()
    private val characterSheetRepository = CharacterSheetRepository(application)
    private val spellRepository = SpellRepository(application)

    var authMode = MutableLiveData(REGISTER)

    val waitingFor = MutableLiveData(listOf(SEARCHING_FOR_CHARACTER, FETCHING_SPELLS))
    val progressMax = MutableLiveData(0)
    val progress = MutableLiveData(0)

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
            spellRepository.fetchAll(this::setProgress) {
                waitingFor.postValue(waitingFor.value!!.filter { it != FETCHING_SPELLS })
            }
        }
    }

    fun setProgress(progress: Int, max: Int) {
        this.progress.postValue(progress)
        this.progressMax.postValue(max)
    }

    fun getLanguage(callback: (String) -> Unit) {
        thread { preferencesRepository.getLanguage(callback) }
    }

    companion object {
        const val REGISTER = 0
        const val LOGIN = 1

        const val SEARCHING_FOR_CHARACTER = 0
        const val FETCHING_SPELLS = 1

        const val LANGUAGE_LOADED = "LANGUAGE_LOADED"
    }
}