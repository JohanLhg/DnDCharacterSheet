package com.jlahougue.dndcharactersheet.dal.entities.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    """
        SELECT weapon.id,
        cw.cid,
        cw.count,
        weapon.name,
        weapon.test,
        CASE 
            WHEN weapon.two_handed_damage != '' THEN weapon.two_handed_damage || ' ' || weapon.two_handed_damage_type
            ELSE weapon.damage || ' ' || weapon.damage_type
        END AS damage,
        CASE 
            WHEN weapon.throw_range != '' THEN weapon.range || ' / ' || weapon.throw_range
            ELSE weapon.range
        END AS range
        FROM weapon
        INNER JOIN character_weapon AS cw ON weapon.id = cw.wid
    """,
    viewName = WeaponView.TABLE_WEAPON_VIEW
)
class WeaponView(
    @ColumnInfo(name = WEAPON_VIEW_ID)
    var id: Long = 0,
    @ColumnInfo(name = WEAPON_VIEW_CID)
    var cid: Long = 0,
    @ColumnInfo(name = WEAPON_VIEW_COUNT)
    var count: Int = 0,
    @ColumnInfo(name = WEAPON_VIEW_NAME)
    var name: String = "",
    @ColumnInfo(name = WEAPON_VIEW_TEST)
    var test: String = "",
    @ColumnInfo(name = WEAPON_VIEW_DAMAGE)
    var damage: String = "",
    @ColumnInfo(name = WEAPON_VIEW_RANGE)
    var range: String = ""
) {
    companion object {
        const val TABLE_WEAPON_VIEW = "weapon_view"
        const val WEAPON_VIEW_ID = "id"
        const val WEAPON_VIEW_CID = "cid"
        const val WEAPON_VIEW_COUNT = "count"
        const val WEAPON_VIEW_NAME = "name"
        const val WEAPON_VIEW_TEST = "test"
        const val WEAPON_VIEW_DAMAGE = "damage"
        const val WEAPON_VIEW_RANGE = "range"
    }
}