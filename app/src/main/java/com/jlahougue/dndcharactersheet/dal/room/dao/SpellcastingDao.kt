package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Spellcasting
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView

@Dao
interface SpellcastingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spellcasting: Spellcasting)

    @Update
    fun update(spellcasting: Spellcasting)

    @Delete
    fun delete(spellcasting: Spellcasting)

    @Query("DELETE FROM spellcasting WHERE cid = :characterID")
    fun deleteForCharacter(characterID: Long)

    @Query("SELECT * FROM spellcasting_view WHERE cid = :characterID")
    fun get(characterID: Long): LiveData<SpellcastingView>

    @Query("SELECT ability FROM spellcasting WHERE cid = :characterID")
    fun getAbility(characterID: Long): String
}