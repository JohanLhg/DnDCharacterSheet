package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = CharacterSpell.TABLE_CHARACTER_SPELL,
    primaryKeys = [CharacterSpell.CHARACTER_SPELL_CID, CharacterSpell.CHARACTER_SPELL_NAME]
)
class CharacterSpell(
    @ColumnInfo(name = CHARACTER_SPELL_CID)
    var cid: Long = 0,
    @ColumnInfo(name = CHARACTER_SPELL_NAME)
    var spellName: String = "",
    @ColumnInfo(name = CHARACTER_SPELL_UNLOCKED)
    var unlocked: Boolean = false,
    @ColumnInfo(name = CHARACTER_SPELL_PREPARED)
    var prepared: Boolean = false,
    @ColumnInfo(name = CHARACTER_SPELL_ALWAYS_PREPARED)
    var alwaysPrepared: Boolean = false,
    @ColumnInfo(name = CHARACTER_SPELL_HIGHLIGHTED)
    var highlighted: Boolean = false
) {
    companion object {
        const val TABLE_CHARACTER_SPELL = "character_spell"
        const val CHARACTER_SPELL_CID = "cid"
        const val CHARACTER_SPELL_NAME = "spell_name"
        const val CHARACTER_SPELL_UNLOCKED = "unlocked"
        const val CHARACTER_SPELL_PREPARED = "prepared"
        const val CHARACTER_SPELL_ALWAYS_PREPARED = "always_prepared"
        const val CHARACTER_SPELL_HIGHLIGHTED = "highlighted"
    }
}