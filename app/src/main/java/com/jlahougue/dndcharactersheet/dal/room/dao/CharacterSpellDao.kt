package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell.Companion.CHARACTER_SPELL_NAME

@Dao
interface CharacterSpellDao {
    @Insert
    fun insert(characterSpell: CharacterSpell)

    @Update
    fun update(characterSpell: CharacterSpell)

    @Delete
    fun delete(characterSpell: CharacterSpell)

    @MapInfo(keyColumn = CHARACTER_SPELL_NAME)
    @Query("SELECT * FROM character_spell WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<String, CharacterSpell>
}