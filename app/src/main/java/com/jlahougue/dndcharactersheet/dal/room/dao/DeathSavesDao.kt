package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves

@Dao
interface DeathSavesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(deathSaves: DeathSaves)

    @Update
    fun update(deathSaves: DeathSaves)

    @Delete
    fun delete(deathSaves: DeathSaves)

    @Query("DELETE FROM death_saves WHERE cid = :characterID")
    fun deleteForCharacter(characterID: Long)

    @Query("SELECT * FROM death_saves WHERE cid = :characterID")
    fun get(characterID: Long): DeathSaves
}