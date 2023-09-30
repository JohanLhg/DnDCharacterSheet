package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Health

@Dao
interface HealthDao {
    @Insert
    fun insert(health: Health)

    @Update
    fun update(health: Health)

    @Delete
    fun delete(health: Health)
}