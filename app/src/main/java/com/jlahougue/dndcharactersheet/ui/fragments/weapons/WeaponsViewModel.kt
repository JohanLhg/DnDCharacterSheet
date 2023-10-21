package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.WeaponDetail
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterWeaponRepository
import kotlin.concurrent.thread

class WeaponsViewModel(application: Application) : AndroidViewModel(application) {

    private val characterWeaponRepository = CharacterWeaponRepository(application)

    lateinit var weapons: LiveData<List<WeaponView>>

    var characterID = 0L
        set(value) {
            field = value
            weapons = characterWeaponRepository.get(value)
        }

    fun getWeapon(name: String, callback: (WeaponDetail) -> Unit) {
        thread {
            val weapon = characterWeaponRepository.getWeapon(characterID, name)
            callback(weapon)
        }
    }

    fun updateCharacterWeapon(weapon: WeaponDetail) {
        thread {
            characterWeaponRepository.saveToLocal(weapon.getCharacterWeapon())
        }
    }
}