package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.firebase.dao.AbilityDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityModifierView
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityView

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

    companion object {
        const val STRENGTH = "STR"
        const val DEXTERITY = "DEX"
        const val CONSTITUTION = "CON"
        const val INTELLIGENCE = "INT"
        const val WISDOM = "WIS"
        const val CHARISMA = "CHA"
    }
}