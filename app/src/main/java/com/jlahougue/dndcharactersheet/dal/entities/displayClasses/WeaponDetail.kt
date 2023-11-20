package com.jlahougue.dndcharactersheet.dal.entities.displayClasses

import androidx.room.ColumnInfo
import androidx.room.Junction
import androidx.room.Relation
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView

class WeaponDetail(
    @ColumnInfo(name = WeaponView.WEAPON_VIEW_CID)
    var cid: Long = 0,
    @ColumnInfo(name = Weapon.WEAPON_NAME)
    var name: String = "",
    @ColumnInfo(name = CharacterWeapon.CHARACTER_WEAPON_COUNT)
    var count: Int = 0,
    @ColumnInfo(name = CharacterWeapon.CHARACTER_WEAPON_PROFICIENCY)
    var proficiency: Boolean = false,
    @ColumnInfo(name = Weapon.WEAPON_TEST)
    var test: AbilityName = AbilityName.NONE,
    @ColumnInfo(name = WeaponView.WEAPON_VIEW_MODIFIER)
    var modifier: Int = 0,
    @ColumnInfo(name = Weapon.WEAPON_DAMAGE)
    var damage: String = "",
    @ColumnInfo(name = Weapon.WEAPON_DAMAGE_TYPE)
    var damageType: String = "",
    @ColumnInfo(name = Weapon.WEAPON_TWO_HANDED_DAMAGE)
    var twoHandedDamage: String = "",
    @ColumnInfo(name = Weapon.WEAPON_TWO_HANDED_DAMAGE_TYPE)
    var twoHandedDamageType: String = "",
    @ColumnInfo(name = Weapon.WEAPON_RANGE)
    var range: Int = 0,
    @ColumnInfo(name = Weapon.WEAPON_THROW_RANGE_MIN)
    var throwRange: Int = 0,
    @ColumnInfo(name = Weapon.WEAPON_THROW_RANGE_MAX)
    var throwRangeMax: Int = 0,
    @ColumnInfo(name = Weapon.WEAPON_WEIGHT)
    var weight: Int = 0,
    @ColumnInfo(name = Weapon.WEAPON_COST)
    var cost: String = "",
    @ColumnInfo(name = Weapon.WEAPON_DESCRIPTION)
    var description: String = "",
    @Relation(
        parentColumn = Weapon.WEAPON_NAME,
        entityColumn = Property.PROPERTY_NAME,
        associateBy = Junction(WeaponProperty::class)
    )
    var properties: List<Property> = listOf()
) {
    fun getCharacterWeapon(): CharacterWeapon {
        return CharacterWeapon(
            cid = cid,
            name = name,
            count = count,
            proficiency = proficiency
        )
    }
}