package com.jlahougue.dndcharactersheet.domainLayer.apiFetch

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.repositories.DamageTypeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FetchDamageTypesFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val damageTypeRepository = DamageTypeRepository(application)

    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            damageTypeRepository.fetchAll(
                ::cancel,
                ::setProgressMax,
                ::skip,
                ::updateProgress,
                damageTypeRepository::save
            )
            finish()
        }
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke()
    }
}