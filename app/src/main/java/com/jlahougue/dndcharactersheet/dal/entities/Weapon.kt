package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Weapon.TABLE_WEAPON)
class Weapon(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WEAPON_ID)
    var id: Long = 0,
    @ColumnInfo(name = WEAPON_NAME)
    var name: String = "",
    @ColumnInfo(name = WEAPON_DAMAGE)
    var damage: String = "",
    @ColumnInfo(name = WEAPON_DAMAGE_TYPE)
    var damageType: String = "",
    @ColumnInfo(name = WEAPON_RANGE_NORMAL)
    var rangeNormal: Int = 0,
    @ColumnInfo(name = WEAPON_RANGE_LONG)
    var rangeLong: Int = 0,
    @ColumnInfo(name = WEAPON_WEIGHT)
    var weight: Double = 0.0,
    @ColumnInfo(name = WEAPON_COST)
    var cost: String = "",
    @ColumnInfo(name = WEAPON_DESCRIPTION)
    var description: String = "",
    @ColumnInfo(name = WEAPON_SPECIAL)
    var special: String = ""
) {
    companion object {
        const val TABLE_WEAPON = "weapon"
        const val WEAPON_ID = "id"
        const val WEAPON_NAME = "name"
        const val WEAPON_DAMAGE = "damage"
        const val WEAPON_DAMAGE_TYPE = "damage_type"
        const val WEAPON_RANGE_NORMAL = "range_normal"
        const val WEAPON_RANGE_LONG = "range_long"
        const val WEAPON_WEIGHT = "weight"
        const val WEAPON_COST = "cost"
        const val WEAPON_DESCRIPTION = "description"
        const val WEAPON_SPECIAL = "special"
    }
}