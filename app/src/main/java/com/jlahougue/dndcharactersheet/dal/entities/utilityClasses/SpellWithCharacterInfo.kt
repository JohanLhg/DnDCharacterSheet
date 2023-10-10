package com.jlahougue.dndcharactersheet.dal.entities.utilityClasses

import androidx.room.ColumnInfo
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.Spell

class SpellWithCharacterInfo(
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_CID)
    var cid: Long = 0,
    @ColumnInfo(name = Spell.SPELL_NAME)
    var name: String = "",
    @ColumnInfo(name = Spell.SPELL_LEVEL)
    var level: Int = 0,
    @ColumnInfo(name = Spell.SPELL_CASTING_TIME)
    var castingTime: String = "",
    @ColumnInfo(name = Spell.SPELL_RANGE)
    var range: String = "",
    @ColumnInfo(name = Spell.SPELL_DURATION)
    var duration: String = "",
    @ColumnInfo(name = Spell.SPELL_DESCRIPTION)
    var description: String = "",
    @ColumnInfo(name = Spell.SPELL_HIGHER_LEVELS)
    var higherLevels: String = "",
    @ColumnInfo(name = Spell.SPELL_DAMAGE_TYPE)
    var damageType: String = "",
    @JvmField
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_UNLOCKED)
    var unlocked: Boolean = false,
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_PREPARED)
    var prepared: Boolean = false,
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_HIGHLIGHTED)
    var highlighted: Boolean = false
) {
    fun setUnlocked(unlocked: Boolean) {
        this.unlocked = unlocked
        if (unlocked) highlighted = false
        else prepared = false
    }

    fun getCharacterSpell(): CharacterSpell {
        return CharacterSpell(
            cid,
            name,
            unlocked,
            prepared,
            highlighted
        )
    }
}