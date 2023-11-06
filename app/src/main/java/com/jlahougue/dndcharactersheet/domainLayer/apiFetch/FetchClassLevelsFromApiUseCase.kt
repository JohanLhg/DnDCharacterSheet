package com.jlahougue.dndcharactersheet.domainLayer.apiFetch

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.repositories.ClassLevelRepository
import com.jlahougue.dndcharactersheet.dal.repositories.ClassSpellSlotRepository

class FetchClassLevelsFromApiUseCase(application: Application) {
    private val classLevelRepository = ClassLevelRepository(application)
    private val classSpellSlotRepository = ClassSpellSlotRepository(application)

    suspend operator fun invoke(clazz: String) {
        classLevelRepository.fetchLevelsForClass(
            clazz,
            classLevelRepository::save,
            classSpellSlotRepository::save
        )
    }
}