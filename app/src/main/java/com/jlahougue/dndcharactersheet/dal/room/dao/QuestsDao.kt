package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Quests

@Dao
interface QuestsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quests: Quests)

    @Update
    fun update(quests: Quests)

    @Delete
    fun delete(quests: Quests)

    @Query("SELECT content FROM quests WHERE cid = :characterID")
    fun get(characterID: Long): String
}