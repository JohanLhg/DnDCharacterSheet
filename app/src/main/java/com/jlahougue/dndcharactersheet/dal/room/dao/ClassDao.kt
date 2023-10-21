package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Class

@Dao
interface ClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(clazz: Class)

    @Update
    fun update(clazz: Class)

    @Delete
    fun delete(clazz: Class)

    @Query("SELECT * FROM class")
    fun get(): List<Class>

    @Query("SELECT class_name FROM class")
    fun getNames(): List<String>
}