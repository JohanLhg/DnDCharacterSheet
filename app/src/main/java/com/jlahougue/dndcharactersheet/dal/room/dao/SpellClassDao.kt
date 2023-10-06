package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass

@Dao
interface SpellClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spellClass: SpellClass)

    @Update
    fun update(spellClass: SpellClass)

    @Delete
    fun delete(spellClass: SpellClass)
}