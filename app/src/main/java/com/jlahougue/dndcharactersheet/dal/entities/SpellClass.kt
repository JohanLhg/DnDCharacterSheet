package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = SpellClass.TABLE_SPELL_CLASS,
    primaryKeys = [SpellClass.SPELL_CLASS_CLASS, SpellClass.SPELL_CLASS_SPELL]
)
class SpellClass(
    @ColumnInfo(name = SPELL_CLASS_CLASS)
    var className: String = "",
    @ColumnInfo(name = SPELL_CLASS_SPELL)
    var spellName: String = ""
) {
    companion object {
        const val TABLE_SPELL_CLASS = "spell_class"
        const val SPELL_CLASS_CLASS = "class"
        const val SPELL_CLASS_SPELL = "spell"
    }
}