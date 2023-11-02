package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.dal.repositories.ClassLevelRepository
import com.jlahougue.dndcharactersheet.dal.repositories.ClassSpellSlotRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FetchClassLevelsFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val classLevelRepository = ClassLevelRepository(application)
    private val classSpellSlotRepository = ClassSpellSlotRepository(application)

    private val _saveClassLevelFlow = MutableSharedFlow<ClassLevelObjects>()
    private val saveClassLevelFlow = _saveClassLevelFlow.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            saveClassLevelFlow.collect {
                classLevelRepository.save(it.classLevel)
                classSpellSlotRepository.save(it.classSpellSlots)
                progress()
            }
        }
    }

    operator fun invoke(clazz: String) {
        classLevelRepository.fetchLevelsForClass(
            clazz,
            ::cancel,
            ::setProgressMax,
            ::save
        )
    }

    operator fun invoke(clazz: String, onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke(clazz)
    }

    private fun save(classLevel: ClassLevel, classSpellSlots: List<ClassSpellSlot>) {
        CoroutineScope(Dispatchers.IO).launch {
            _saveClassLevelFlow.emit(ClassLevelObjects(classLevel, classSpellSlots))
        }
    }

    private class ClassLevelObjects(
        val classLevel: ClassLevel,
        val classSpellSlots: List<ClassSpellSlot>
    )
}