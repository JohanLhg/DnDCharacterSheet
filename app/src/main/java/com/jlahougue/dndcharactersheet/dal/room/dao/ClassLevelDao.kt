package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel

@Dao
interface ClassLevelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(classLevel: ClassLevel): Long

    @Update
    fun update(classLevel: ClassLevel)

    @Delete
    fun delete(classLevel: ClassLevel)

    @Query("SELECT * FROM class_level WHERE class = :name ORDER BY level ASC")
    fun get(name: String): List<ClassLevel>
}