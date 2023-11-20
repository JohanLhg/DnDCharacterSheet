package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import android.content.Context
import com.jlahougue.dndcharactersheet.dal.entities.Skill
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName
import com.jlahougue.dndcharactersheet.dal.entities.enums.SkillName
import com.jlahougue.dndcharactersheet.dal.entities.views.SkillView
import com.jlahougue.dndcharactersheet.dal.firebase.dao.SkillDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SkillRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).skillDao()
    private val firebaseDao = SkillDao()

    fun create(characterID: Long) {
        insert(Skill(characterID, SkillName.ACROBATICS, AbilityName.DEXTERITY))
        insert(Skill(characterID, SkillName.ANIMAL_HANDLING, AbilityName.WISDOM))
        insert(Skill(characterID, SkillName.ARCANA, AbilityName.INTELLIGENCE))
        insert(Skill(characterID, SkillName.ATHLETICS, AbilityName.STRENGTH))
        insert(Skill(characterID, SkillName.DECEPTION, AbilityName.CHARISMA))
        insert(Skill(characterID, SkillName.HISTORY, AbilityName.INTELLIGENCE))
        insert(Skill(characterID, SkillName.INSIGHT, AbilityName.WISDOM))
        insert(Skill(characterID, SkillName.INTIMIDATION, AbilityName.CHARISMA))
        insert(Skill(characterID, SkillName.INVESTIGATION, AbilityName.INTELLIGENCE))
        insert(Skill(characterID, SkillName.MEDICINE, AbilityName.WISDOM))
        insert(Skill(characterID, SkillName.NATURE, AbilityName.INTELLIGENCE))
        insert(Skill(characterID, SkillName.PERCEPTION, AbilityName.WISDOM))
        insert(Skill(characterID, SkillName.PERFORMANCE, AbilityName.CHARISMA))
        insert(Skill(characterID, SkillName.PERSUASION, AbilityName.CHARISMA))
        insert(Skill(characterID, SkillName.RELIGION, AbilityName.INTELLIGENCE))
        insert(Skill(characterID, SkillName.SLEIGHT_OF_HAND, AbilityName.DEXTERITY))
        insert(Skill(characterID, SkillName.STEALTH, AbilityName.DEXTERITY))
        insert(Skill(characterID, SkillName.SURVIVAL, AbilityName.WISDOM))
    }

    fun insert(skill: Skill) {
        roomDao.insert(skill)
        firebaseDao.save(skill)
    }

    fun saveToLocal(skill: Skill) = roomDao.insert(skill)

    fun deleteForCharacter(characterID: Long) = roomDao.deleteForCharacter(characterID)

    fun update(skill: SkillView) {
        update(Skill(skill.cid, skill.name, skill.modifierType, skill.proficiency))
    }

    fun update(skill: Skill) {
        roomDao.update(skill)
        firebaseDao.save(skill)
    }

    fun getMap(characterID: Long) = roomDao.getMap(characterID)

    fun get(characterID: Long) = roomDao.get(characterID)

    companion object {
        fun getFullName(context: Context, name: SkillName, modifierType: AbilityName): String {
            return "${name.getName(context)} (${modifierType.getShortName(context)})"
        }
    }
}