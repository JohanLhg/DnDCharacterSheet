package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Preferences
import com.jlahougue.dndcharactersheet.dal.entities.enums.Language

@Dao
interface PreferencesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(preferences: Preferences)

    @Update
    fun update(preferences: Preferences)

    @Delete
    fun delete(preferences: Preferences)

    @Query("SELECT * FROM preferences WHERE id = 1")
    fun get(): LiveData<Preferences>

    @Query("SELECT language FROM preferences WHERE id = 1")
    fun getLanguage(): Language?
}