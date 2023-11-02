package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.repositories.ClassRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FetchClassesFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val classRepository = ClassRepository(application)

    private val fetchClassLevelsFromApiUseCase = FetchClassLevelsFromApiUseCase(application)

    private val _saveClassFlow = MutableSharedFlow<Class>()
    private val saveClassFlow = _saveClassFlow.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            saveClassFlow.collect {
                classRepository.save(it)
                fetchClassLevelsFromApiUseCase(it.name) {
                    progress()
                }
            }
        }
    }

    operator fun invoke() {
        classRepository.fetchAll(
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

    private fun save(clazz: Class) {
        CoroutineScope(Dispatchers.IO).launch {
            _saveClassFlow.emit(clazz)
        }
    }
}