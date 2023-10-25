package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon.Companion.CHARACTER_WEAPON_NAME
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.WeaponDetail
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView

@Dao
interface CharacterWeaponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterWeapon: CharacterWeapon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterWeapons: List<CharacterWeapon>)

    @Update
    fun update(characterWeapon: CharacterWeapon)

    @Delete
    fun delete(characterWeapon: CharacterWeapon)

    @Query("SELECT * FROM weapon_view WHERE cid = :characterID AND count > 0")
    fun get(characterID: Long): LiveData<List<WeaponView>>

    @Transaction
    @Query("""
        WITH my_character_weapon AS (
            SELECT name, proficiency
            FROM character_weapon
            WHERE cid = :characterID
        ),
        my_weapon_view AS (
            SELECT name, count, modifier
            FROM weapon_view
            WHERE cid = :characterID
        )
        SELECT
            :characterID AS cid,
            weapon.*,
            wv.count,
            wv.modifier,
            cw.proficiency
        FROM weapon
        LEFT JOIN my_weapon_view AS wv ON weapon.weapon_name = wv.name
        LEFT JOIN my_character_weapon AS cw ON weapon.weapon_name = cw.name
        WHERE weapon.weapon_name = :name
    """)
    fun getWeapon(characterID: Long, name: String): WeaponDetail

    @MapInfo(keyColumn = CHARACTER_WEAPON_NAME)
    @Query("SELECT * FROM character_weapon WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<String, CharacterWeapon>

    @Query("""
        SELECT weapon_name
        FROM weapon
        WHERE weapon_name NOT IN (
            SELECT name
            FROM character_weapon
            WHERE cid = :characterID
            AND count > 0
        )
    """)
    fun getNotOwned(characterID: Long): List<String>
}