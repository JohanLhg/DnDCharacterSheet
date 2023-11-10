package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Money

@Dao
interface MoneyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(money: Money)

    @Update
    fun update(money: Money)

    @Delete
    fun delete(money: Money)
    
    @Query("DELETE FROM money WHERE cid = :characterID")
    fun deleteForCharacter(characterID: Long)

    @Query("SELECT * FROM money WHERE cid = :characterID")
    fun get(characterID: Long): Money
}