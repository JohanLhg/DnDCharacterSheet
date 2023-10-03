package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.entities.Stats
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.dal.repositories.HealthRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SkillRepository
import com.jlahougue.dndcharactersheet.dal.repositories.StatsRepository
import com.jlahougue.dndcharactersheet.dal.room.views.SkillView
import kotlin.concurrent.thread

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    private val abilityRepository = AbilityRepository(application)
    private val skillRepository = SkillRepository(application)
    private val statsRepository = StatsRepository(application)
    private val healthRepository = HealthRepository(application)

    val abilities = MutableLiveData<List<Ability>>(null)
    lateinit var skills: LiveData<List<SkillView>>
    val stats = MutableLiveData<Stats>(null)
    val health = MutableLiveData<Health>(null)

    var characterID = 0L
        set(value) {
            field = value
            skills = skillRepository.get(value)
            thread {
                abilities.postValue(abilityRepository.get(value))
                stats.postValue(statsRepository.get(value))
                health.postValue(healthRepository.get(value))
            }
        }
}