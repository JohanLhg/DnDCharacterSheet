package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell

@Dao
interface CharacterSpellDao {
    @Insert
    fun insert(characterSpell: CharacterSpell)

    @Update
    fun update(characterSpell: CharacterSpell)

    @Delete
    fun delete(characterSpell: CharacterSpell)
}