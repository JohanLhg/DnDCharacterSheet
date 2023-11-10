package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Notes

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Notes)

    @Update
    fun update(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("DELETE FROM notes WHERE cid = :characterID")
    fun deleteForCharacter(characterID: Long)

    @Query("SELECT content FROM notes WHERE cid = :characterID")
    fun get(characterID: Long): String
}