package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Class.TABLE_CLASS)
class Class(
    @PrimaryKey
    @ColumnInfo(name = CLASS_NAME)
    val name: String,
    @ColumnInfo(name = CLASS_HIT_DICE)
    val hitDice: Int,
    @ColumnInfo(name = CLASS_PROF_ARMOR)
    val profArmor: String,
    @ColumnInfo(name = CLASS_PROF_WEAPONS)
    val profWeapons: String,
    @ColumnInfo(name = CLASS_PROF_TOOLS)
    val profTools: String,
    @ColumnInfo(name = CLASS_HAS_SPELLCASTING)
    val hasSpellcasting: Boolean,
    @ColumnInfo(name = CLASS_SPELLCASTING_ABILITY)
    val spellcastingAbility: String
) {
    companion object {
        const val TABLE_CLASS = "class"
        const val CLASS_NAME = "name"
        const val CLASS_HIT_DICE = "hit_dice"
        const val CLASS_PROF_ARMOR = "prof_armor"
        const val CLASS_PROF_WEAPONS = "prof_weapons"
        const val CLASS_PROF_TOOLS = "prof_tools"
        const val CLASS_HAS_SPELLCASTING = "has_spellcasting"
        const val CLASS_SPELLCASTING_ABILITY = "spellcasting_ability"
    }
}