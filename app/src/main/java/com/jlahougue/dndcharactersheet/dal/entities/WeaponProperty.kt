package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = WeaponProperty.TABLE_WEAPON_PROPERTY,
    primaryKeys = [WeaponProperty.WEAPON_PROPERTY_WEAPON_ID, WeaponProperty.WEAPON_PROPERTY_PROPERTY]
)
class WeaponProperty(
    @ColumnInfo(name = WEAPON_PROPERTY_WEAPON_ID)
    var weaponId: Long = 0,
    @ColumnInfo(name = WEAPON_PROPERTY_PROPERTY)
    var property: String = ""
) {
    companion object {
        const val TABLE_WEAPON_PROPERTY = "weapon_property"
        const val WEAPON_PROPERTY_WEAPON_ID = "weapon_id"
        const val WEAPON_PROPERTY_PROPERTY = "property"
    }
}