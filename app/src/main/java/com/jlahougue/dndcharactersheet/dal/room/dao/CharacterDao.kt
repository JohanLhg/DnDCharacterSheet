package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.entities.Character.Companion.CHARACTER_ID
import com.jlahougue.dndcharactersheet.dal.entities.Character.Companion.CHARACTER_IS_FAVORITE

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: Character): Long

    @Update
    fun update(character: Character)

    @Delete
    fun delete(character: Character)

    @Query("DELETE FROM character WHERE id = :characterID")
    fun delete(characterID: Long)

    @Query("SELECT * FROM character")
    fun get(): LiveData<List<Character>>

    @Query("SELECT * FROM character WHERE id = :characterID")
    fun get(characterID: Long): Character

    @Query("SELECT bonus FROM proficiency_view WHERE cid = :characterID")
    fun getProficiency(characterID: Long): LiveData<Int>

    @Query("SELECT id FROM character")
    fun getIDs(): List<Long>

    @Query("SELECT EXISTS(SELECT id FROM character)")
    fun exists(): Boolean

    @Query("SELECT EXISTS(SELECT id FROM character WHERE is_favorite = 1)")
    fun hasFavorite(): Boolean

    @Query("SELECT id FROM character WHERE is_favorite = 1")
    fun getFavorite(): Long

    @Query("UPDATE character SET is_favorite = (id = :id AND :isFavorite)")
    fun updateFavorite(id: Long, isFavorite: Boolean)
    @MapInfo(keyColumn = CHARACTER_ID, valueColumn = CHARACTER_IS_FAVORITE)
    @Query("SELECT id, is_favorite FROM character")
    fun getFavoriteMap(): Map<Long, Boolean>
}