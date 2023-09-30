package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Stats

@Dao
interface StatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stats: Stats)

    @Update
    fun update(stats: Stats)

    @Delete
    fun delete(stats: Stats)

    @Query("SELECT * FROM stats WHERE cid = :characterID")
    fun get(characterID: Long): Stats
}