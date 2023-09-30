package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Preferences

@Dao
interface PreferencesDao {
    @Insert
    fun insert(preferences: Preferences)

    @Update
    fun update(preferences: Preferences)

    @Delete
    fun delete(preferences: Preferences)
}