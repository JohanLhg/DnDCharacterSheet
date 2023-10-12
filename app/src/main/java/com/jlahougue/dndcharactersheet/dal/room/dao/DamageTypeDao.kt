package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.DamageType

@Dao
interface DamageTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(damageType: DamageType)

    @Update
    fun update(damageType: DamageType)

    @Delete
    fun delete(damageType: DamageType)

    @Query("SELECT name FROM damage_type")
    fun getNames(): List<String>
}