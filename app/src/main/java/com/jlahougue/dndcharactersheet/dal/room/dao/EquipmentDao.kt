package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Equipment

@Dao
interface EquipmentDao {
    @Insert
    fun insert(equipment: Equipment)

    @Update
    fun update(equipment: Equipment)

    @Delete
    fun delete(equipment: Equipment)
}