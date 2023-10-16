package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterWeaponRepository
import kotlin.concurrent.thread

class WeaponsViewModel(application: Application) : AndroidViewModel(application) {

    private val characterWeaponRepository = CharacterWeaponRepository(application)

    val weapons = MutableLiveData<List<WeaponView>>(null)

    var characterID = 0L
        set(value) {
            field = value
            thread {
                weapons.postValue(characterWeaponRepository.get(value))
            }
        }
}