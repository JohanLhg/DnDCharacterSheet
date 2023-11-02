package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.repositories.WeaponPropertyRepository
import com.jlahougue.dndcharactersheet.dal.repositories.WeaponRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FetchWeaponsFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val weaponRepository = WeaponRepository(application)
    private val weaponPropertyRepository = WeaponPropertyRepository(application)

    private val _saveWeaponFlow = MutableSharedFlow<WeaponObjects>()
    private val saveWeaponFlow = _saveWeaponFlow.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            saveWeaponFlow.collect {
                weaponRepository.save(it.weapon)
                weaponPropertyRepository.save(it.properties)
                progress()
            }
        }
    }

    operator fun invoke() {
        weaponRepository.fetchAll(
            ::cancel,
            ::setProgressMax,
            ::skip,
            ::save
        )
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        weaponRepository.fetchAll(
            ::cancel,
            ::setProgressMax,
            ::skip,
            ::save
        )
    }

    private fun save(weapon: Weapon, properties: List<WeaponProperty>) {
        CoroutineScope(Dispatchers.IO).launch {
            _saveWeaponFlow.emit(WeaponObjects(weapon, properties))
        }
    }

    private class WeaponObjects(
        val weapon: Weapon,
        val properties: List<WeaponProperty>
    )
}