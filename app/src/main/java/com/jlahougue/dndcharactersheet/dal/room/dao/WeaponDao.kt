package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Weapon

@Dao
interface WeaponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weapon: Weapon): Long

    @Update
    fun update(weapon: Weapon)

    @Delete
    fun delete(weapon: Weapon)

    @Query("SELECT name FROM weapon")
    fun getNames(): List<String>
}