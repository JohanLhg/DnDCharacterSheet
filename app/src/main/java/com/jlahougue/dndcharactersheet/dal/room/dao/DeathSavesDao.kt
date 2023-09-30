package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves

@Dao
interface DeathSavesDao {
    @Insert
    fun insert(deathSaves: DeathSaves)

    @Update
    fun update(deathSaves: DeathSaves)

    @Delete
    fun delete(deathSaves: DeathSaves)
}