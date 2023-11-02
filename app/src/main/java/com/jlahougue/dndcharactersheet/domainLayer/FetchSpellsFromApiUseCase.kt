package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import com.jlahougue.dndcharactersheet.dal.repositories.DamageTypeRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellClassRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellDamageRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FetchSpellsFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val damageTypeRepository = DamageTypeRepository(application)
    private val spellRepository = SpellRepository(application)
    private val spellClassRepository = SpellClassRepository(application)
    private val spellDamageRepository = SpellDamageRepository(application)

    private val _saveSpellFlow = MutableSharedFlow<SpellObjects>()
    private val saveSpellFlow = _saveSpellFlow.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            saveSpellFlow.collect {
                spellRepository.save(it.spell)
                spellClassRepository.save(it.classes)
                spellDamageRepository.save(it.damages)
                progress()
            }
        }
    }

    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            spellRepository.fetchAll(
                damageTypeRepository.getNames(),
                ::cancel,
                ::setProgressMax,
                ::skip,
                ::save
            )
        }
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke()
    }

    private fun save(spell: Spell, classes: List<SpellClass>, damages: List<SpellDamage>) {
        CoroutineScope(Dispatchers.IO).launch {
            _saveSpellFlow.emit(SpellObjects(spell, classes, damages))
        }
    }

    private class SpellObjects(
        val spell: Spell,
        val classes: List<SpellClass>,
        val damages: List<SpellDamage>
    )
}