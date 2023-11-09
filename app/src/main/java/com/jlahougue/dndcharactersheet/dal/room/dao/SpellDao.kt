package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface SpellDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spell: Spell): Long

    @Update
    fun update(spell: Spell)

    @Delete
    fun delete(spell: Spell)

    @Query("SELECT spell_id FROM spell")
    fun getIds(): List<String>

    @Transaction
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
        SELECT DISTINCT
            :characterID AS cid,
            spell.*, 
            my_character_spell.unlocked, 
            my_character_spell.prepared,
            my_character_spell.always_prepared,
            my_character_spell.highlighted
        FROM spell
        INNER JOIN spell_class ON spell.spell_id = spell_class.spell_id
        LEFT JOIN my_character
        LEFT JOIN my_character_spell ON spell.spell_id = my_character_spell.spell_id
        WHERE spell.level <= my_character.max_spell_level
        ORDER BY spell.level ASC, spell.name ASC
    """)
    fun get(characterID: Long): List<SpellWithCharacterInfo>

    @Transaction
    @Query("""
        WITH my_character_spell AS (
            SELECT character_spell.*
            FROM character_spell
            WHERE character_spell.cid = :characterID
        )
        SELECT DISTINCT
            :characterID AS cid,
            spell.*, 
            my_character_spell.unlocked, 
            my_character_spell.prepared, 
            my_character_spell.always_prepared,
            my_character_spell.highlighted
        FROM spell
        INNER JOIN spell_class ON spell.spell_id = spell_class.spell_id
        LEFT JOIN my_character_spell ON spell.spell_id = my_character_spell.spell_id
        WHERE spell.level = :spellLevel
        ORDER BY spell.level ASC, spell.name ASC
    """)
    fun get(characterID: Long, spellLevel: Int): List<SpellWithCharacterInfo>

    @Transaction
    @Query("""
        SELECT
            :characterID AS cid, 
            spell.*, 
            character_spell.unlocked, 
            character_spell.prepared, 
            character_spell.always_prepared,
            character_spell.highlighted
        FROM spell 
        INNER JOIN character_spell ON spell.spell_id = character_spell.spell_id
        WHERE character_spell.cid = :characterID 
        AND character_spell.unlocked = 1
    """)
    fun getUnlocked(characterID: Long): List<SpellWithCharacterInfo>

    @Transaction
    @Query("""
        SELECT
            :characterID AS cid, 
            spell.*, 
            character_spell.unlocked, 
            character_spell.prepared, 
            character_spell.always_prepared,
            character_spell.highlighted
        FROM spell 
        INNER JOIN character_spell ON spell.spell_id = character_spell.spell_id
        WHERE character_spell.cid = :characterID 
        AND character_spell.unlocked = 1
    """)
    fun getUnlockedFlow(characterID: Long): Flow<List<SpellWithCharacterInfo>>

    @Transaction
    @Query("""
        SELECT
            :characterID AS cid, 
            spell.*, 
            character_spell.unlocked, 
            character_spell.prepared, 
            character_spell.always_prepared,
            character_spell.highlighted
        FROM spell 
        INNER JOIN character_spell ON spell.spell_id = character_spell.spell_id
        WHERE character_spell.cid = :characterID 
        AND character_spell.unlocked = 1
        AND spell.level = :spellLevel
    """)
    fun getUnlocked(characterID: Long, spellLevel: Int): List<SpellWithCharacterInfo>
}