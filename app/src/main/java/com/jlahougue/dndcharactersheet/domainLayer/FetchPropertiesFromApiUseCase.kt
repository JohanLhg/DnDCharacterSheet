package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.repositories.PropertyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FetchPropertiesFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val propertyRepository = PropertyRepository(application)

    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            propertyRepository.fetchAll(
                ::cancel,
                ::setProgressMax,
                ::skip,
                ::updateProgress,
                propertyRepository::save
            )
            finish()
        }
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke()
    }
}