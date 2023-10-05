package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.entities.Stats
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import com.jlahougue.dndcharactersheet.dal.repositories.HealthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SkillRepository
import com.jlahougue.dndcharactersheet.dal.repositories.StatsRepository
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.room.views.SkillView
import kotlin.concurrent.thread

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository = CharacterRepository(application)
    private val abilityRepository = AbilityRepository(application)
    private val skillRepository = SkillRepository(application)
    private val statsRepository = StatsRepository(application)
    private val healthRepository = HealthRepository(application)

    lateinit var abilities: LiveData<List<AbilityView>>
    lateinit var skills: LiveData<List<SkillView>>
    val stats = MutableLiveData<Stats>(null)
    lateinit var proficiency: LiveData<Int>
    val health = MutableLiveData<Health>(null)
    lateinit var hitDiceNbr: LiveData<Int>

    var characterID = 0L
        set(value) {
            field = value
            abilities = abilityRepository.get(value)
            skills = skillRepository.get(value)
            proficiency = characterRepository.getProficiency(value)
            hitDiceNbr = healthRepository.getHitDiceNbr(value)
            thread {
                stats.postValue(statsRepository.get(value))
                health.postValue(healthRepository.get(value))
            }
        }

    fun updateAbilityValue(ability: AbilityView) {
        thread {
            abilityRepository.updateValue(ability)
        }
    }

    fun updateAbilityProficiency(ability: AbilityView) {
        thread {
            abilityRepository.updateProficiency(ability)
        }
    }

    fun updateSkill(skill: SkillView) {
        thread {
            skillRepository.update(skill)
        }
    }

    fun updateStats(stats: Stats) {
        thread {
            statsRepository.update(stats)
        }
    }

    fun updateHealth(health: Health) {
        thread {
            healthRepository.update(health)
        }
    }
}