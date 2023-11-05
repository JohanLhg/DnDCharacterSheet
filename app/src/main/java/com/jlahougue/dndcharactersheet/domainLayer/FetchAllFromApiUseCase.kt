package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.extensions.setCollectorIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FetchAllFromApiUseCase(application: Application) {
    private val fetchDamageTypesFromApiUseCase = FetchDamageTypesFromApiUseCase(application)
    private val fetchSpellsFromApiUseCase = FetchSpellsFromApiUseCase(application)
    private val fetchWeaponsFromApiUseCase = FetchWeaponsFromApiUseCase(application)
    private val fetchClassesFromApiUseCase = FetchClassesFromApiUseCase(application)
    private val fetchPropertiesFromApiUseCase = FetchPropertiesFromApiUseCase(application)

    private val identifierUseCase = mapOf(
        DAMAGE_TYPES to fetchDamageTypesFromApiUseCase,
        PROPERTIES to fetchPropertiesFromApiUseCase,
        CLASSES to fetchClassesFromApiUseCase,
        WEAPONS to fetchWeaponsFromApiUseCase,
        SPELLS to fetchSpellsFromApiUseCase
    )

    private val _notifyFinished = MutableSharedFlow<Int>(10)
    private val notifyFinished = _notifyFinished.asSharedFlow()

    private var waitingFor = listOf(DAMAGE_TYPES, PROPERTIES, CLASSES, WEAPONS, SPELLS)

    private val _currentIdentifier = MutableStateFlow(DAMAGE_TYPES)
    val currentIdentifier = _currentIdentifier.asStateFlow()

    private val _progressMax = MutableStateFlow(0)
    val progressMax = _progressMax.asStateFlow()

    private val _progress = MutableStateFlow(0)
    val progress = _progress.asStateFlow()

    private val _finished = MutableStateFlow(false)
    private val finished = _finished.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            currentIdentifier.collect {
                setupProgressCollectors(it)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            notifyFinished.collect { identifier ->
                waitingFor = waitingFor.filter { identifier != it }
                if (waitingFor.isEmpty()) _finished.emit(true)
                else _currentIdentifier.emit(waitingFor.first())
            }
        }
        setupFinishedCollectors()
    }

    operator fun invoke(onFinished: () -> Unit) {
        setCollectorIO(finished) {
            if (it) onFinished()
        }

        fetchDamageTypesFromApiUseCase {
            fetchSpellsFromApiUseCase()
        }
        fetchPropertiesFromApiUseCase()
        fetchClassesFromApiUseCase()
        fetchWeaponsFromApiUseCase()
    }

    private fun setupProgressCollectors(identifier: Int) {
        setupProgressMaxCollector(identifier)
        setupProgressCollector(identifier)
    }

    private fun setupProgressMaxCollector(identifier: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            identifierUseCase[identifier]?.progressMax?.collect {
                _progressMax.emit(it)
            }
        }
    }

    private fun setupProgressCollector(identifier: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            identifierUseCase[identifier]?.progress?.collect {
                _progress.emit(it)
            }
        }
    }

    private fun setupFinishedCollectors() {
        identifierUseCase.forEach { (identifier, fetcher) ->
            setupFinishedCollector(fetcher, identifier)
        }
    }

    private fun setupFinishedCollector(fetcher: FetchFromApiUseCase, identifier: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            fetcher.finished.collect {
                if (it) _notifyFinished.emit(identifier)
            }
        }
    }

    companion object {
        const val DAMAGE_TYPES = 0
        const val PROPERTIES = 1
        const val CLASSES = 2
        const val WEAPONS = 3
        const val SPELLS = 4
    }
}