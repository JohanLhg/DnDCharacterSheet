package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.firebase.dao.AbilityDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class AbilityRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).abilityDao()
    private val firebaseDao = AbilityDao()

    fun create(characterID: Long) {
        insert(Ability(cid = characterID, name = AbilityName.STRENGTH))
        insert(Ability(cid = characterID, name = AbilityName.DEXTERITY))
        insert(Ability(cid = characterID, name = AbilityName.CONSTITUTION))
        insert(Ability(cid = characterID, name = AbilityName.INTELLIGENCE))
        insert(Ability(cid = characterID, name = AbilityName.WISDOM))
        insert(Ability(cid = characterID, name = AbilityName.CHARISMA))
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
        roomDao.updateValue(ability.cid, ability.name.code, ability.value)
        firebaseDao.updateValue(ability.cid, ability.name.code, ability.value)
    }

    fun updateProficiency(ability: AbilityView) {
        roomDao.updateProficiency(ability.cid, ability.name.code, ability.proficiency)
        firebaseDao.updateProficiency(ability.cid, ability.name.code, ability.proficiency)
    }

    fun get(characterID: Long) = roomDao.get(characterID)

    fun getMap(characterID: Long) = roomDao.getMap(characterID)

    fun getAttackStats(characterID: Long) = roomDao.getAttackStats(characterID)
}