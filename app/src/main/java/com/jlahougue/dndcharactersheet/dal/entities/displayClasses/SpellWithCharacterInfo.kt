package com.jlahougue.dndcharactersheet.dal.entities.displayClasses

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage

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
    @ColumnInfo(name = Spell.SPELL_COMPONENTS)
    var components: String = "",
    @ColumnInfo(name = Spell.SPELL_MATERIALS)
    var materials: String = "",
    @ColumnInfo(name = Spell.SPELL_RITUAL)
    var ritual: Boolean = false,
    @ColumnInfo(name = Spell.SPELL_CONCENTRATION)
    var concentration: Boolean = false,
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
    var highlighted: Boolean = false,
    @Relation(
        parentColumn = Spell.SPELL_NAME,
        entityColumn = SpellClass.SPELL_CLASS_SPELL
    )
    var classes: List<SpellClass> = listOf(),
    @Relation(
        parentColumn = Spell.SPELL_NAME,
        entityColumn = SpellDamage.SPELL_DAMAGE_NAME
    )
    var damages: List<SpellDamage> = listOf()
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

    fun getClassesString(): String {
        return classes.joinToString(", ") { it.className }
    }

    fun copy(): SpellWithCharacterInfo {
        return SpellWithCharacterInfo(
            cid,
            name,
            level,
            castingTime,
            range,
            components,
            materials,
            ritual,
            concentration,
            duration,
            description,
            higherLevels,
            damageType,
            unlocked,
            prepared,
            highlighted
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is SpellWithCharacterInfo) return false
        return cid == other.cid &&
                name == other.name &&
                level == other.level &&
                castingTime == other.castingTime &&
                range == other.range &&
                components == other.components &&
                materials == other.materials &&
                ritual == other.ritual &&
                concentration == other.concentration &&
                duration == other.duration &&
                description == other.description &&
                higherLevels == other.higherLevels &&
                damageType == other.damageType &&
                unlocked == other.unlocked &&
                prepared == other.prepared &&
                highlighted == other.highlighted
    }

    override fun hashCode(): Int {
        var result = cid.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + level
        result = 31 * result + castingTime.hashCode()
        result = 31 * result + range.hashCode()
        result = 31 * result + components.hashCode()
        result = 31 * result + materials.hashCode()
        result = 31 * result + ritual.hashCode()
        result = 31 * result + concentration.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + higherLevels.hashCode()
        result = 31 * result + damageType.hashCode()
        result = 31 * result + unlocked.hashCode()
        result = 31 * result + prepared.hashCode()
        result = 31 * result + highlighted.hashCode()
        return result
    }
}