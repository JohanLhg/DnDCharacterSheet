package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Stat

@Dao
interface StatDao {
    @Insert
    fun insert(stat: Stat)

    @Update
    fun update(stat: Stat)

    @Delete
    fun delete(stat: Stat)
}