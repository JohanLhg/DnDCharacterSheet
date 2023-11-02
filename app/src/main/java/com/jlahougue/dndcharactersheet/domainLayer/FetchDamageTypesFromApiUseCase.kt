package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import com.jlahougue.dndcharactersheet.dal.repositories.DamageTypeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FetchDamageTypesFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val damageTypeRepository = DamageTypeRepository(application)

    private val _saveDamageTypeFlow = MutableSharedFlow<DamageType>()
    private val saveDamageTypeFlow = _saveDamageTypeFlow.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            saveDamageTypeFlow.collect {
                damageTypeRepository.save(it)
                progress()
            }
        }
    }

    operator fun invoke() {
        damageTypeRepository.fetchAll(
            ::cancel,
            ::setProgressMax,
            ::skip,
            ::save
        )
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke()
    }

    private fun save(damageType: DamageType) {
        CoroutineScope(Dispatchers.IO).launch {
            _saveDamageTypeFlow.emit(damageType)
        }
    }
}