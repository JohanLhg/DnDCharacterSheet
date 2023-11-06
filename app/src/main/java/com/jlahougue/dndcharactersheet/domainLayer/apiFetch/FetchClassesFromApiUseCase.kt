package com.jlahougue.dndcharactersheet.domainLayer.apiFetch

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.repositories.ClassRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FetchClassesFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val classRepository = ClassRepository(application)

    private val fetchClassLevelsFromApiUseCase = FetchClassLevelsFromApiUseCase(application)

    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            classRepository.fetchAll(
                ::cancel,
                ::setProgressMax,
                ::skip,
                classRepository::save,
                ::fetchClassLevels
            )
            finish()
        }
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke()
    }

    private suspend fun fetchClassLevels(clazz: String) {
        fetchClassLevelsFromApiUseCase(clazz)
        updateProgress()
    }
}