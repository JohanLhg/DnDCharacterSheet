package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Ability.Companion.ABILITY_NAME
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats.Companion.MELEE_MODIFIER
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats.Companion.PROFICIENCY_BONUS
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats.Companion.RANGED_MODIFIER
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.STRENGTH

@Dao
interface AbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ability: Ability)

    @Update
    fun update(ability: Ability)

    @Query("UPDATE ability SET value = :value WHERE cid = :cid AND name = :name")
    fun updateValue(cid: Long, name: String, value: Int)

    @Query("UPDATE ability SET proficiency = :proficiency WHERE cid = :cid AND name = :name")
    fun updateProficiency(cid: Long, name: String, proficiency: Boolean)

    @Delete
    fun delete(ability: Ability)
    @Query("SELECT * FROM ability_view WHERE cid = :characterID ORDER BY name = 'STR' DESC, name = 'DEX' DESC, name = 'CON' DESC, name = 'INT' DESC, name = 'WIS' DESC, name = 'CHA' DESC")
    fun get(characterID: Long): LiveData<List<AbilityView>>
    @MapInfo(keyColumn = ABILITY_NAME)
    @Query("SELECT * FROM ability WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<String, Ability>

    @Query("""
        WITH modifiers AS (
            SELECT * 
            FROM ability_modifier_view 
            WHERE cid = :characterID
        )
        SELECT 
            bonus AS $PROFICIENCY_BONUS, 
            (SELECT modifier FROM ability_modifier_view WHERE name = '$STRENGTH') AS $MELEE_MODIFIER, 
            (SELECT modifier FROM ability_modifier_view WHERE name = '$DEXTERITY') AS $RANGED_MODIFIER 
        FROM proficiency_view 
        WHERE cid = :characterID
    """)
    fun getAttackStats(characterID: Long): LiveData<AttackStats>
}