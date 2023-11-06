package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.entities.Stats
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.entities.views.SkillView
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.DeathSavesRepository
import com.jlahougue.dndcharactersheet.dal.repositories.HealthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SkillRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SpellSlotRepository
import com.jlahougue.dndcharactersheet.dal.repositories.StatsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        val CURRENT = 0
        val TEMPORARY = 1
    }

    private val characterRepository = CharacterRepository(application)
    private val abilityRepository = AbilityRepository(application)
    private val skillRepository = SkillRepository(application)
    private val statsRepository = StatsRepository(application)
    private val healthRepository = HealthRepository(application)
    private val deathSavesRepository = DeathSavesRepository(application)
    private val spellSlotRepository = SpellSlotRepository(application)

    lateinit var abilities: LiveData<List<AbilityView>>
    lateinit var skills: LiveData<List<SkillView>>
    val stats = MutableLiveData<Stats>(null)
    lateinit var proficiency: LiveData<Int>
    val healthMode = MutableLiveData(CURRENT)
    lateinit var health: LiveData<Health>
    lateinit var hitDiceNbr: LiveData<Int>
    val deathSaves = MutableLiveData<DeathSaves>(null)

    var characterID = 0L
        set(value) {
            field = value
            abilities = abilityRepository.get(value)
            skills = skillRepository.get(value)
            proficiency = characterRepository.getProficiency(value)
            hitDiceNbr = healthRepository.getHitDiceNbr(value)
            health = healthRepository.getLive(value)
            CoroutineScope(Dispatchers.IO).launch {
                stats.postValue(statsRepository.get(value))
                deathSaves.postValue(deathSavesRepository.get(value))
            }
        }

    fun updateAbilityValue(ability: AbilityView) {
        CoroutineScope(Dispatchers.IO).launch {
            abilityRepository.updateValue(ability)
        }
    }

    fun updateAbilityProficiency(ability: AbilityView) {
        CoroutineScope(Dispatchers.IO).launch {
            abilityRepository.updateProficiency(ability)
        }
    }

    fun updateSkill(skill: SkillView) {
        CoroutineScope(Dispatchers.IO).launch {
            skillRepository.update(skill)
        }
    }

    fun updateStats(stats: Stats) {
        CoroutineScope(Dispatchers.IO).launch {
            statsRepository.update(stats)
        }
    }

    fun updateHealth(
        maxHp: Int? = null,
        addToMaxHp: Int? = null,
        currentHp: Int? = null,
        addToCurrentHp: Int? = null,
        temporaryHp: Int? = null,
        addToTemporaryHp: Int? = null,
        hitDice: String? = null,
        hitDiceUsed: Int? = null
    ) {
        if (health.value == null) return
        CoroutineScope(Dispatchers.IO).launch {
            val newHealth = health.value!!
            if (maxHp != null) newHealth.maxHp = maxHp
            if (addToMaxHp != null) newHealth.maxHp += addToMaxHp
            if (currentHp != null) newHealth.currentHp = currentHp
            if (addToCurrentHp != null) newHealth.currentHp += addToCurrentHp
            if (temporaryHp != null) newHealth.temporaryHp = temporaryHp
            if (addToTemporaryHp != null) newHealth.temporaryHp += addToTemporaryHp
            if (hitDice != null) newHealth.hitDice = hitDice
            if (hitDiceUsed != null) newHealth.hitDiceUsed = hitDiceUsed
            healthRepository.update(newHealth)
        }
    }

    fun updateDeathSaves(deathSaves: DeathSaves) {
        CoroutineScope(Dispatchers.IO).launch {
            this@StatsViewModel.deathSaves.postValue(deathSaves)
            deathSavesRepository.update(deathSaves)
        }
    }

    fun longRest() {
        CoroutineScope(Dispatchers.IO).launch {
            healthRepository.longRest(characterID)
            spellSlotRepository.restoreSpellSlots(characterID)
        }
    }
}