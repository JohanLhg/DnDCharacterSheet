package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot.Companion.SPELL_SLOT_LEVEL
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot.Companion.SPELL_SLOT_USED

@Dao
interface SpellSlotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spellSlot: SpellSlot)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(spellSlots: List<SpellSlot>)

    @Update
    fun update(spellSlot: SpellSlot)

    @Delete
    fun delete(spellSlot: SpellSlot)

    @Query("SELECT * FROM spell_slot WHERE cid = :characterID")
    fun get(characterID: Long): List<SpellSlot>

    @MapInfo(keyColumn = SPELL_SLOT_LEVEL, valueColumn = SPELL_SLOT_USED)
    @Query("SELECT level, slots_used FROM spell_slot WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<Int, Int>
}