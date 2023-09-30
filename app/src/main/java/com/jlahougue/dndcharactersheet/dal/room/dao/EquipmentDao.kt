package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Equipment

@Dao
interface EquipmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(equipment: Equipment)

    @Update
    fun update(equipment: Equipment)

    @Delete
    fun delete(equipment: Equipment)

    @Query("SELECT content FROM equipment WHERE cid = :characterID")
    fun get(characterID: Long): String
}