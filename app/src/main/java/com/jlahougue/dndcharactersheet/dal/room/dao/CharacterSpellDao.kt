package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell.Companion.CHARACTER_SPELL_SID
import com.jlahougue.dndcharactersheet.dal.entities.views.CharacterSpellStatsView

@Dao
interface CharacterSpellDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterSpell: CharacterSpell)

    @Update
    fun update(characterSpell: CharacterSpell)

    @Delete
    fun delete(characterSpell: CharacterSpell)

    @Query("DELETE FROM character_spell WHERE cid = :characterID")
    fun deleteForCharacter(characterID: Long)

    @Query("SELECT * FROM character_spell WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<@MapColumn(columnName = CHARACTER_SPELL_SID) String, CharacterSpell>

    @Query("SELECT * FROM character_spell_stats_view WHERE cid = :characterID")
    fun getCharacterSpellStats(characterID: Long): LiveData<CharacterSpellStatsView>
}