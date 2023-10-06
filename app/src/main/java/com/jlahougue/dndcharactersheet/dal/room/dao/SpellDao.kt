package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Spell

@Dao
interface SpellDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spell: Spell)

    @Update
    fun update(spell: Spell)

    @Delete
    fun delete(spell: Spell)

    @Query("SELECT name FROM spell")
    fun getNames(): List<String>
}