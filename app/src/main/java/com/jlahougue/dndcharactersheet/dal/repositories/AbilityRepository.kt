package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import android.content.Context
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.firebase.dao.AbilityDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class AbilityRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).abilityDao()
    private val firebaseDao = AbilityDao()

    fun create(characterID: Long) {
        insert(Ability(cid = characterID, name = STRENGTH))
        insert(Ability(cid = characterID, name = DEXTERITY))
        insert(Ability(cid = characterID, name = CONSTITUTION))
        insert(Ability(cid = characterID, name = INTELLIGENCE))
        insert(Ability(cid = characterID, name = WISDOM))
        insert(Ability(cid = characterID, name = CHARISMA))
    }

    fun insert(ability: Ability) {
        roomDao.insert(ability)
        firebaseDao.save(ability)
    }

    fun saveToLocal(ability: Ability) = roomDao.insert(ability)

    fun deleteForCharacter(characterID: Long) = roomDao.deleteForCharacter(characterID)

    fun update(ability: Ability) {
        roomDao.update(ability)
        firebaseDao.save(ability)
    }

    fun updateValue(ability: AbilityView) {
        roomDao.updateValue(ability.cid, ability.name, ability.value)
        firebaseDao.updateValue(ability.cid, ability.name, ability.value)
    }

    fun updateProficiency(ability: AbilityView) {
        roomDao.updateProficiency(ability.cid, ability.name, ability.proficiency)
        firebaseDao.updateProficiency(ability.cid, ability.name, ability.proficiency)
    }

    fun get(characterID: Long) = roomDao.get(characterID)

    fun getMap(characterID: Long) = roomDao.getMap(characterID)

    fun getAttackStats(characterID: Long) = roomDao.getAttackStats(characterID)

    companion object {
        const val STRENGTH = "STR"
        const val DEXTERITY = "DEX"
        const val CONSTITUTION = "CON"
        const val INTELLIGENCE = "INT"
        const val WISDOM = "WIS"
        const val CHARISMA = "CHA"

        fun getDatabaseCode(name: String) = when(name) {
            "Strength" -> STRENGTH
            "Dexterity" -> DEXTERITY
            "Constitution" -> CONSTITUTION
            "Intelligence" -> INTELLIGENCE
            "Wisdom" -> WISDOM
            "Charisma" -> CHARISMA
            else -> ""
        }

        fun getName(context: Context, name: String) = when(name) {
            STRENGTH -> context.getString(R.string.strength)
            DEXTERITY -> context.getString(R.string.dexterity)
            CONSTITUTION -> context.getString(R.string.constitution)
            INTELLIGENCE -> context.getString(R.string.intelligence)
            WISDOM -> context.getString(R.string.wisdom)
            CHARISMA -> context.getString(R.string.charisma)
            else -> ""
        }

        fun getNameId(name: String?) = when(name) {
            STRENGTH -> R.string.strength
            DEXTERITY -> R.string.dexterity
            CONSTITUTION -> R.string.constitution
            INTELLIGENCE -> R.string.intelligence
            WISDOM -> R.string.wisdom
            CHARISMA -> R.string.charisma
            else -> R.string.empty
        }

        fun getModifierName(context: Context, modifierType: String) = when(modifierType) {
            STRENGTH -> context.getString(R.string.modifier_strength)
            DEXTERITY -> context.getString(R.string.modifier_dexterity)
            CONSTITUTION -> context.getString(R.string.modifier_constitution)
            INTELLIGENCE -> context.getString(R.string.modifier_intelligence)
            WISDOM -> context.getString(R.string.modifier_wisdom)
            CHARISMA -> context.getString(R.string.modifier_charisma)
            else -> ""
        }
    }
}