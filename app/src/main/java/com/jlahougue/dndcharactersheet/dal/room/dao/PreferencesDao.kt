package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Preferences

@Dao
interface PreferencesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(preferences: Preferences)

    @Update
    fun update(preferences: Preferences)

    @Delete
    fun delete(preferences: Preferences)

    @Query("UPDATE preferences SET language = :language WHERE id = 1")
    fun setLanguage(language: String)

    @Query("SELECT language FROM preferences WHERE id = 1")
    fun getLanguage(): String?
}