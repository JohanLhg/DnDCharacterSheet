package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = SpellDamage.TABLE_SPELL_DAMAGE,
    primaryKeys = [SpellDamage.SPELL_DAMAGE_NAME, SpellDamage.SPELL_DAMAGE_LEVEL]
)
class SpellDamage(
    @ColumnInfo(name = SPELL_DAMAGE_NAME)
    var name: String = "",
    @ColumnInfo(name = SPELL_DAMAGE_LEVEL)
    var level: Int = 0,
    @ColumnInfo(name = SPELL_DAMAGE_DICE)
    var dice: Int = 0,
    @ColumnInfo(name = SPELL_DAMAGE_DICE_NUMBER)
    var diceNumber: Int = 0
) {
    companion object {
        const val TABLE_SPELL_DAMAGE = "spell_damage"
        const val SPELL_DAMAGE_NAME = "name"
        const val SPELL_DAMAGE_LEVEL = "level"
        const val SPELL_DAMAGE_DICE = "dice"
        const val SPELL_DAMAGE_DICE_NUMBER = "dice_number"
    }
}