package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.repositories.WeaponPropertyRepository
import com.jlahougue.dndcharactersheet.dal.repositories.WeaponRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FetchWeaponsFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val weaponRepository = WeaponRepository(application)
    private val weaponPropertyRepository = WeaponPropertyRepository(application)

    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            weaponRepository.fetchAll(
                ::cancel,
                ::setProgressMax,
                ::skip,
                ::updateProgress,
                weaponRepository::save,
                weaponPropertyRepository::save
            )
            finish()
        }
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke()
    }
}