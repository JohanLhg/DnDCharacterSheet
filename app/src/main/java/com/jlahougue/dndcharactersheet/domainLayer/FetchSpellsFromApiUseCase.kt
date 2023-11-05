package com.jlahougue.dndcharactersheet.domainLayer

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.repositories.DamageTypeRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellClassRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellDamageRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FetchSpellsFromApiUseCase(application: Application): FetchFromApiUseCase() {
    private val damageTypeRepository = DamageTypeRepository(application)
    private val spellRepository = SpellRepository(application)
    private val spellClassRepository = SpellClassRepository(application)
    private val spellDamageRepository = SpellDamageRepository(application)

    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            spellRepository.fetchAll(
                damageTypeRepository.getNames(),
                ::cancel,
                ::setProgressMax,
                ::skip,
                ::updateProgress,
                spellRepository::save,
                spellClassRepository::save,
                spellDamageRepository::save
            )
            finish()
        }
    }

    public override operator fun invoke(onFinished: () -> Unit) {
        super.invoke(onFinished)
        invoke()
    }
}