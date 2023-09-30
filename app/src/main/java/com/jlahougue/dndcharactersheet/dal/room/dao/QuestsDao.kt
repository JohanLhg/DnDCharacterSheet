package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Quests

@Dao
interface QuestsDao {
    @Insert
    fun insert(quests: Quests)

    @Update
    fun update(quests: Quests)

    @Delete
    fun delete(quests: Quests)
}