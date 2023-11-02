package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot

@Dao
interface ClassSpellSlotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(classSpellSlot: ClassSpellSlot)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(classSpellSlots: List<ClassSpellSlot>)

    @Update
    fun update(classSpellSlot: ClassSpellSlot)

    @Delete
    fun delete(classSpellSlot: ClassSpellSlot)

    @Query("SELECT * FROM class_spell_slot WHERE class = :name ORDER BY class_level ASC, spell_level ASC")
    fun get(name: String): List<ClassSpellSlot>
}