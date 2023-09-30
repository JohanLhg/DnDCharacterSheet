package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Ability.Companion.ABILITY_NAME

@Dao
interface AbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ability: Ability)

    @Update
    fun update(ability: Ability)

    @Delete
    fun delete(ability: Ability)

    @Query("SELECT * FROM ability WHERE cid = :characterID")
    fun get(characterID: Long): List<Ability>

    @MapInfo(keyColumn = ABILITY_NAME)
    @Query("SELECT * FROM ability WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<String, Ability>
}