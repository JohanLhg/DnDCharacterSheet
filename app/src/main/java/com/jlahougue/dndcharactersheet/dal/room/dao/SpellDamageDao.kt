package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage

@Dao
interface SpellDamageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spellDamage: SpellDamage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spellDamages: List<SpellDamage>)

    @Update
    fun update(spellDamage: SpellDamage)

    @Delete
    fun delete(spellDamage: SpellDamage)
}