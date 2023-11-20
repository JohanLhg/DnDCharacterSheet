package com.jlahougue.dndcharactersheet.ui.authentication

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jlahougue.dndcharactersheet.dal.entities.enums.Language
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSheetRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository
import com.jlahougue.dndcharactersheet.domainLayer.apiFetch.FetchAllFromApiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val REGISTER = 0
        const val LOGIN = 1

        const val SEARCHING_FOR_CHARACTER = -1

        const val LANGUAGE_LOADED = "LANGUAGE_LOADED"
    }

    private val preferencesRepository = PreferencesRepository(application)
    private val authRepository = AuthRepository()
    private val characterSheetRepository = CharacterSheetRepository(application)

    private val fetchAllFromApiUseCase = FetchAllFromApiUseCase(application)

    var authMode = MutableLiveData(REGISTER)
    val currentIdentifier = MutableLiveData(SEARCHING_FOR_CHARACTER)
    val currentProgressMax = MutableLiveData(0)
    val currentProgress = MutableLiveData(0)
    val finished = MutableLiveData(false)

    init {
        viewModelScope.launch {
            fetchAllFromApiUseCase.currentIdentifier.collect {
                currentIdentifier.postValue(it)
            }
        }
        viewModelScope.launch {
            fetchAllFromApiUseCase.progressMax.collect {
                currentProgressMax.postValue(it)
            }
        }
        viewModelScope.launch {
            fetchAllFromApiUseCase.progress.collect {
                currentProgress.postValue(it)
            }
        }
    }

    fun isLoggedIn() = authRepository.isLoggedIn()

    fun register(email: String, password: String, callback: (Boolean) -> Unit) {
        authRepository.register(email, password, callback)
    }

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        authRepository.login(email, password, callback)
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return true
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return true
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return true
            }
        }
        return false
    }

    fun load(context: Context) {
        if(!isOnline(context)) return finished.postValue(true)

        viewModelScope.launch {
            characterSheetRepository.createCharacterIfNotExists {
                fetchAllFromApiUseCase {
                    finished.postValue(true)
                }
            }
        }
    }

    fun getLanguage(callback: (Language) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.getLanguage(callback)
        }
    }
}