package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.repositories.PropertyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FetchPropertiesFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val propertyRepository = PropertyRepository(application)

    private val _savePropertyFlow = MutableSharedFlow<Property>()
    private val savePropertyFlow = _savePropertyFlow.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            savePropertyFlow.collect {
                propertyRepository.save(it)
                progress()
            }
        }
    }

    operator fun invoke() {
        propertyRepository.fetchAll(
            ::cancel,
            ::setProgressMax,
            ::skip,
            ::save
        )
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        propertyRepository.fetchAll(
            ::cancel,
            ::setProgressMax,
            ::skip,
            ::save
        )
    }

    private fun save(property: Property) {
        CoroutineScope(Dispatchers.IO).launch {
            _savePropertyFlow.emit(property)
        }
    }
}