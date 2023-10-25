package com.jlahougue.dndcharactersheet.ui.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.repositories.AuthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSheetRepository
import com.jlahougue.dndcharactersheet.dal.repositories.ClassRepository
import com.jlahougue.dndcharactersheet.dal.repositories.DamageTypeRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository
import com.jlahougue.dndcharactersheet.dal.repositories.PropertyRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.WeaponRepository
import kotlin.concurrent.thread

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val REGISTER = 0
        const val LOGIN = 1

        const val SEARCHING_FOR_CHARACTER = 0
        const val FETCHING_CLASSES = 1
        const val FETCHING_SPELLS = 2
        const val FETCHING_DAMAGE_TYPES = 3
        const val FETCHING_WEAPON_PROPERTIES = 4
        const val FETCHING_WEAPONS = 5

        const val LANGUAGE_LOADED = "LANGUAGE_LOADED"
    }

    private val preferencesRepository = PreferencesRepository(application)
    private val authRepository = AuthRepository()
    private val characterSheetRepository = CharacterSheetRepository(application)
    private val classRepository = ClassRepository(application)
    private val spellRepository = SpellRepository(application)
    private val damageTypeRepository = DamageTypeRepository(application)
    private val propertyRepository = PropertyRepository(application)
    private val weaponRepository = WeaponRepository(application)

    var authMode = MutableLiveData(REGISTER)
    val waitingFor = MutableLiveData(listOf(SEARCHING_FOR_CHARACTER, FETCHING_CLASSES,
        FETCHING_SPELLS, FETCHING_DAMAGE_TYPES, FETCHING_WEAPON_PROPERTIES, FETCHING_WEAPONS))
    val currentProgressMax = MutableLiveData(0)
    val currentProgress = MutableLiveData(0)
    private var progressMax = mapOf<Int, Int>()
    private var progress = mapOf<Int, Int>()

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
        thread {
            classRepository.fetchAll(
                FETCHING_CLASSES,
                this::setProgressMax,
                this::updateProgress
            )
        }
        thread {
            spellRepository.fetchAll(
                FETCHING_SPELLS,
                this::setProgressMax,
                this::updateProgress
            )
        }
        thread {
            damageTypeRepository.fetchAll(
                FETCHING_DAMAGE_TYPES,
                this::setProgressMax,
                this::updateProgress
            )
        }
        thread {
            propertyRepository.fetchAll(
                FETCHING_WEAPON_PROPERTIES,
                this::setProgressMax,
                this::updateProgress
            )
        }
        thread {
            weaponRepository.fetchAll(
                FETCHING_WEAPONS,
                this::setProgressMax,
                this::updateProgress
            )
        }
    }

    @Synchronized
    private fun setProgressMax(key: Int, max: Int) {
        progress = progress.plus(key to 0)
        progressMax = progressMax.plus(key to max)
    }

    @Synchronized
    private fun updateProgress(key: Int) {
        val progressValue = (progress[key] ?: 0) + 1
        progress = progress.plus(key to progressValue)

        if (key == waitingFor.value!![0]) {
            currentProgressMax.postValue(progressMax[key])
            currentProgress.postValue(progress[key])
        }

        if (progress[key] == progressMax[key]) {
            waitingFor.postValue(waitingFor.value!!.filter { it != key })
        }

        println("Still waiting for :")
        waitingFor.value!!.forEach { println("$it : ${progress[it]} / ${progressMax[it]}}") }
    }

    fun getLanguage(callback: (String) -> Unit) {
        thread { preferencesRepository.getLanguage(callback) }
    }
}