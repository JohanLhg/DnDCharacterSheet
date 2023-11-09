package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot.Companion.SPELL_SLOT_LEVEL
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot.Companion.SPELL_SLOT_USED
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM spell_slot_view WHERE cid = :characterID")
    fun getLive(characterID: Long): LiveData<List<SpellSlotView>>

    @Query("SELECT * FROM spell_slot_view WHERE cid = :characterID")
    fun getFlow(characterID: Long): Flow<List<SpellSlotView>>

    @Query("SELECT level, slots_used FROM spell_slot WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<@MapColumn(columnName = SPELL_SLOT_LEVEL) Int,
            @MapColumn(columnName = SPELL_SLOT_USED) Int>
}