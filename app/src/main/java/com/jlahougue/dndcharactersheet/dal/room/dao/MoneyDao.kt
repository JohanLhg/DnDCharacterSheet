package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Money

@Dao
interface MoneyDao {
    @Insert
    fun insert(money: Money)

    @Update
    fun update(money: Money)

    @Delete
    fun delete(money: Money)
}