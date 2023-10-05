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
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityModifierView
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityView

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
}