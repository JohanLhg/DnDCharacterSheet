package com.jlahougue.dndcharactersheet.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository
import kotlin.concurrent.thread

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val preferencesRepository = PreferencesRepository(application)
    private val authRepository = AuthRepository()

    var characterID = MutableLiveData(-1L)

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

    companion object {
        const val CHARACTER_ID = "CID"
    }
}