package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Skill
import com.jlahougue.dndcharactersheet.dal.firebase.dao.SkillDao
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.STRENGTH
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.INTELLIGENCE
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.WISDOM
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.CHARISMA
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SkillRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).skillDao()
    private val firebaseDao = SkillDao()

    fun create(characterID: Long) {
        insert(Skill(characterID, ACROBATICS, DEXTERITY))
        insert(Skill(characterID, ANIMAL_HANDLING, WISDOM))
        insert(Skill(characterID, ARCANA, INTELLIGENCE))
        insert(Skill(characterID, ATHLETICS, STRENGTH))
        insert(Skill(characterID, DECEPTION, CHARISMA))
        insert(Skill(characterID, HISTORY, INTELLIGENCE))
        insert(Skill(characterID, INSIGHT, WISDOM))
        insert(Skill(characterID, INTIMIDATION, CHARISMA))
        insert(Skill(characterID, INVESTIGATION, INTELLIGENCE))
        insert(Skill(characterID, MEDICINE, WISDOM))
        insert(Skill(characterID, NATURE, INTELLIGENCE))
        insert(Skill(characterID, PERCEPTION, WISDOM))
        insert(Skill(characterID, PERFORMANCE, CHARISMA))
        insert(Skill(characterID, PERSUASION, CHARISMA))
        insert(Skill(characterID, RELIGION, INTELLIGENCE))
        insert(Skill(characterID, SLEIGHT_OF_HAND, DEXTERITY))
        insert(Skill(characterID, STEALTH, DEXTERITY))
        insert(Skill(characterID, SURVIVAL, WISDOM))
    }

    fun insert(skill: Skill) {
        roomDao.insert(skill)
        firebaseDao.insert(skill)
    }

    fun update(skill: Skill) {
        roomDao.update(skill)
        firebaseDao.update(skill)
    }

    companion object {
        const val ACROBATICS = "ACROBATICS"
        const val ANIMAL_HANDLING = "ANIMAL_HANDLING"
        const val ARCANA = "ARCANA"
        const val ATHLETICS = "ATHLETICS"
        const val DECEPTION = "DECEPTION"
        const val HISTORY = "HISTORY"
        const val INSIGHT = "INSIGHT"
        const val INTIMIDATION = "INTIMIDATION"
        const val INVESTIGATION = "INVESTIGATION"
        const val MEDICINE = "MEDICINE"
        const val NATURE = "NATURE"
        const val PERCEPTION = "PERCEPTION"
        const val PERFORMANCE = "PERFORMANCE"
        const val PERSUASION = "PERSUASION"
        const val RELIGION = "RELIGION"
        const val SLEIGHT_OF_HAND = "SLEIGHT_OF_HAND"
        const val STEALTH = "STEALTH"
        const val SURVIVAL = "SURVIVAL"
    }
}