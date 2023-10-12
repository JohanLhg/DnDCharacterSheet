package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty

@Dao
interface WeaponPropertyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weaponProperty: WeaponProperty)

    @Update
    fun update(weaponProperty: WeaponProperty)

    @Delete
    fun delete(weaponProperty: WeaponProperty)
}