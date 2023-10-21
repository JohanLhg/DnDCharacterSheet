package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = SpellClass.TABLE_SPELL_CLASS,
    primaryKeys = [Spell.SPELL_NAME, Class.CLASS_NAME]
)
class SpellClass(
    @ColumnInfo(name = Spell.SPELL_NAME)
    var className: String = "",
    @ColumnInfo(name = Class.CLASS_NAME, index = true)
    var spellName: String = ""
) {
    companion object {
        const val TABLE_SPELL_CLASS = "spell_class"
    }
}