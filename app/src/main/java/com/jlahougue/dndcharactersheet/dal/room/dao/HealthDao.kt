package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Health

@Dao
interface HealthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(health: Health)

    @Update
    fun update(health: Health)

    @Delete
    fun delete(health: Health)

    @Query("SELECT * FROM health WHERE cid = :characterID")
    fun get(characterID: Long): Health

    @Query("SELECT level FROM character WHERE id = :characterID")
    fun getHitDiceNbr(characterID: Long): LiveData<Int>
}