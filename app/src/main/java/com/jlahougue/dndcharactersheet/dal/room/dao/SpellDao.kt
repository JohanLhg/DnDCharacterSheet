package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.utilityClasses.SpellWithCharacterInfo

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

    @Query("""
        WITH my_character AS (
            SELECT character.*,
            CASE 
                    WHEN level < 3  THEN 1
                    WHEN level < 5  THEN 2
                    WHEN level < 7  THEN 3
                    WHEN level < 9  THEN 4
                    WHEN level < 11 THEN 5
                    WHEN level < 13 THEN 6
                    WHEN level < 15 THEN 7
                    WHEN level < 17 THEN 8
                    ELSE 9
                END AS max_spell_level
            FROM character WHERE id = :characterID
        ), my_character_spell AS (
            SELECT character_spell.*
            FROM character_spell
            WHERE character_spell.cid = :characterID
        )
        SELECT 
            :characterID AS cid,
            spell.*, 
            my_character_spell.unlocked, 
            my_character_spell.prepared, 
            my_character_spell.highlighted
        FROM spell
        INNER JOIN spell_class ON spell.name = spell_class.spell
        LEFT JOIN my_character
        LEFT JOIN my_character_spell ON spell.name = my_character_spell.spell_name
        WHERE spell.level <= my_character.max_spell_level
        AND spell_class.class = my_character.class
        ORDER BY spell.level ASC, spell.name ASC
    """)
    fun get(characterID: Long): List<SpellWithCharacterInfo>

    @Query("""
        SELECT
            :characterID AS cid, 
            spell.*, 
            character_spell.unlocked, 
            character_spell.prepared, 
            character_spell.highlighted
        FROM spell 
        INNER JOIN character_spell ON spell.name = character_spell.spell_name
        WHERE character_spell.cid = :characterID 
        AND character_spell.unlocked = 1
    """)
    fun getUnlocked(characterID: Long): List<SpellWithCharacterInfo>
}