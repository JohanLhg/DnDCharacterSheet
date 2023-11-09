package com.jlahougue.dndcharactersheet.dal.entities.displayClasses

import androidx.room.ColumnInfo
import androidx.room.Junction
import androidx.room.Relation
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage

class SpellWithCharacterInfo(
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_CID)
    var cid: Long = 0,
    @ColumnInfo(name = Spell.SPELL_ID)
    var id: String = "",
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
    @JvmField
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_PREPARED)
    var prepared: Boolean = false,
    @JvmField
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_ALWAYS_PREPARED)
    var alwaysPrepared: Boolean = false,
    @ColumnInfo(name = CharacterSpell.CHARACTER_SPELL_HIGHLIGHTED)
    var highlighted: Boolean = false,
    @Relation(
        parentColumn = Spell.SPELL_ID,
        entityColumn = Class.CLASS_NAME,
        associateBy = Junction(SpellClass::class)
    )
    var classes: List<Class> = listOf(),
    @Relation(
        parentColumn = Spell.SPELL_ID,
        entityColumn = SpellDamage.SPELL_DAMAGE_SID
    )
    var damages: List<SpellDamage> = listOf()
) {
    fun setUnlocked(unlocked: Boolean) {
        this.unlocked = unlocked
        if (unlocked) highlighted = false
        else {
            prepared = false
            alwaysPrepared = false
        }
    }

    fun setPrepared(prepared: Boolean) {
        this.prepared = prepared
        if (prepared) alwaysPrepared = false
    }

    fun setAlwaysPrepared(alwaysPrepared: Boolean) {
        this.alwaysPrepared = alwaysPrepared
        if (alwaysPrepared) prepared = false
    }

    fun getCharacterSpell() = CharacterSpell(
        cid,
        id,
        unlocked,
        prepared,
        alwaysPrepared,
        highlighted
    )

    fun copy(
        cid: Long = this.cid,
        id: String = this.id,
        name: String = this.name,
        level: Int = this.level,
        castingTime: String = this.castingTime,
        range: String = this.range,
        components: String = this.components,
        materials: String = this.materials,
        ritual: Boolean = this.ritual,
        concentration: Boolean = this.concentration,
        duration: String = this.duration,
        description: String = this.description,
        higherLevels: String = this.higherLevels,
        damageType: String = this.damageType,
        unlocked: Boolean = this.unlocked,
        prepared: Boolean = this.prepared,
        alwaysPrepared: Boolean = this.alwaysPrepared,
        highlighted: Boolean = this.highlighted,
        classes: List<Class> = this.classes.map { it.copy() },
        damages: List<SpellDamage> = this.damages.map { it.copy() }
    ) = SpellWithCharacterInfo(
        cid,
        id,
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
        alwaysPrepared,
        highlighted,
        classes,
        damages
    )

    override fun toString() = "$name ($level)"

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is SpellWithCharacterInfo) return false
        return cid == other.cid &&
                id == other.id &&
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
                alwaysPrepared == other.alwaysPrepared &&
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
        result = 31 * result + alwaysPrepared.hashCode()
        result = 31 * result + highlighted.hashCode()
        return result
    }
}