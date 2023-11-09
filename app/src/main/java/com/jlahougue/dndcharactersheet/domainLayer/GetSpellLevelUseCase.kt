package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellLevel
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellSlotRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GetSpellLevelUseCase(application: Application) {
    private val spellSlotRepository = SpellSlotRepository(application)
    private val spellRepository = SpellRepository(application)

    private lateinit var _spellSlots: Flow<List<SpellSlotView>>
    private lateinit var spellSlots: StateFlow<List<SpellSlotView>>

    private lateinit var _spells: Flow<List<SpellWithCharacterInfo>>
    private lateinit var spells: StateFlow<List<SpellWithCharacterInfo>>

    private val _spellLevels = MutableStateFlow<List<SpellLevel>>(listOf())
    private val spellLevels = _spellLevels.asStateFlow()

    operator fun invoke(context: CoroutineScope, characterID: Long): StateFlow<List<SpellLevel>> {
        _spellSlots = spellSlotRepository.getFlow(characterID)
        _spells = spellRepository.getUnlockedFlow(characterID)

        context.launch {
            spellSlots = _spellSlots.stateIn(context)
            spells = _spells.stateIn(context)

            context.launch {
                spellSlots.collect {
                    updateSpellLevels()
                }
            }
            context.launch {
                spells.collect {
                    updateSpellLevels()
                }
            }
        }

        return spellLevels
    }

    private suspend fun updateSpellLevels() {
        val spellLevels = mutableListOf<SpellLevel>()
        for (spellSlot in spellSlots.value) {
            val spells = spells.value.filter { it.level == spellSlot.level }
            if (spells.isEmpty()) continue
            spellLevels.add(SpellLevel(spellSlot, spells))
        }
        _spellLevels.emit(spellLevels)
    }
}