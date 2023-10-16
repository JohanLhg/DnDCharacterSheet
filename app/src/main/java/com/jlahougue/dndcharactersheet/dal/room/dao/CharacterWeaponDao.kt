package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon.Companion.CHARACTER_WEAPON_COUNT
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon.Companion.CHARACTER_WEAPON_WID
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView

@Dao
interface CharacterWeaponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterWeapon: CharacterWeapon)

    @Update
    fun update(characterWeapon: CharacterWeapon)

    @Delete
    fun delete(characterWeapon: CharacterWeapon)

    @Query("SELECT * FROM weapon_view WHERE cid = :characterID")
    fun get(characterID: Long): List<WeaponView>

    @MapInfo(keyColumn = CHARACTER_WEAPON_WID, valueColumn = CHARACTER_WEAPON_COUNT)
    @Query("SELECT * FROM character_weapon WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<Long, Int>
}