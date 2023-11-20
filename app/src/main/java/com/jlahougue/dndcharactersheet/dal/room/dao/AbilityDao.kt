package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Ability.Companion.ABILITY_NAME
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats.Companion.MELEE_MODIFIER
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats.Companion.PROFICIENCY_BONUS
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.AttackStats.Companion.RANGED_MODIFIER
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName.Companion.CHA
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName.Companion.CON
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName.Companion.DEX
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName.Companion.INT
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName.Companion.STR
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName.Companion.WIS
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView

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

    @Query("DELETE FROM ability WHERE cid = :characterID")
    fun deleteForCharacter(characterID: Long)

    @Query("""
        SELECT * FROM ability_view 
        WHERE cid = :characterID 
        ORDER BY name = '$STR' DESC, name = '$DEX' DESC, name = '$CON' DESC, 
            name = '$INT' DESC, name = '$WIS' DESC, name = '$CHA' DESC
    """)
    fun get(characterID: Long): LiveData<List<AbilityView>>

    @Query("SELECT * FROM ability WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<@MapColumn(columnName = ABILITY_NAME) String, Ability>

    @Query("""
        WITH modifiers AS (
            SELECT * 
            FROM ability_modifier_view 
            WHERE cid = :characterID
        )
        SELECT 
            bonus AS $PROFICIENCY_BONUS, 
            (SELECT modifier FROM modifiers WHERE name = '$STR') AS $MELEE_MODIFIER, 
            (SELECT modifier FROM modifiers WHERE name = '$DEX') AS $RANGED_MODIFIER 
        FROM proficiency_view 
        WHERE cid = :characterID
    """)
    fun getAttackStats(characterID: Long): LiveData<AttackStats>
}