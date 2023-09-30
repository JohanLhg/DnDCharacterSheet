package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Ability
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
        firebaseDao.insert(ability)
    }

    fun update(ability: Ability) {
        roomDao.update(ability)
        firebaseDao.update(ability)
    }

    fun delete(ability: Ability) {
        roomDao.delete(ability)
        firebaseDao.delete(ability)
    }

    companion object {
        const val STRENGTH = "STR"
        const val DEXTERITY = "DEX"
        const val CONSTITUTION = "CON"
        const val INTELLIGENCE = "INT"
        const val WISDOM = "WIS"
        const val CHARISMA = "CHA"
    }
}