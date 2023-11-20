package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView

@Dao
interface SpellcastingDao {
    @Query("SELECT * FROM spellcasting_view WHERE cid = :characterID")
    fun get(characterID: Long): LiveData<SpellcastingView>
}