package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = CharacterWeapon.TABLE_CHARACTER_WEAPON,
    primaryKeys = [CharacterWeapon.CHARACTER_WEAPON_CID, CharacterWeapon.CHARACTER_WEAPON_WID]
)
class CharacterWeapon(
    @ColumnInfo(name = CHARACTER_WEAPON_CID)
    var cid: Long = 0,
    @ColumnInfo(name = CHARACTER_WEAPON_WID)
    var wid: Long = 0,
    @ColumnInfo(name = CHARACTER_WEAPON_COUNT)
    var count: Int = 0
) {
    companion object {
        const val TABLE_CHARACTER_WEAPON = "character_weapon"
        const val CHARACTER_WEAPON_CID = "cid"
        const val CHARACTER_WEAPON_WID = "wid"
        const val CHARACTER_WEAPON_COUNT = "count"
    }
}