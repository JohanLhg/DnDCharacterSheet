package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Ability

@Dao
interface AbilityDao {
    @Insert
    fun insert(ability: Ability)

    @Update
    fun update(ability: Ability)

    @Delete
    fun delete(ability: Ability)
}