package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = SpellClass.TABLE_SPELL_CLASS,
    primaryKeys = [SpellClass.SPELL_CLASS_CLASS_NAME, SpellClass.SPELL_CLASS_SPELL_SPELL]
)
class SpellClass(
    @ColumnInfo(name = SPELL_CLASS_CLASS_NAME)
    var className: String = "",
    @ColumnInfo(name = SPELL_CLASS_SPELL_SPELL)
    var spellName: String = ""
) {
    companion object {
        const val TABLE_SPELL_CLASS = "spell_class"
        const val SPELL_CLASS_CLASS_NAME = "class"
        const val SPELL_CLASS_SPELL_SPELL = "spell"
    }
}