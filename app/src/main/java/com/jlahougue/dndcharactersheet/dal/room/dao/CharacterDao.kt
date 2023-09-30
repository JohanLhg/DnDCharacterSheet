package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Character

@Dao
interface CharacterDao {
    @Insert
    fun insert(character: Character)

    @Update
    fun update(character: Character)

    @Delete
    fun delete(character: Character)
}