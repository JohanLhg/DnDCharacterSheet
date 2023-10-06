package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Spell

@Dao
interface SpellDao {
    @Insert
    fun insert(spell: Spell)

    @Update
    fun update(spell: Spell)

    @Delete
    fun delete(spell: Spell)
}