package com.jlahougue.dndcharactersheet.dal.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.CHARISMA
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.CONSTITUTION
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.INTELLIGENCE
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.STRENGTH
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.WISDOM

@Entity(tableName = Ability.TABLE_ABILITY, primaryKeys = [Ability.ABILITY_CID, Ability.ABILITY_NAME])
class Ability(
    @ColumnInfo(name = ABILITY_CID)
    var cid: Long = 0,
    @ColumnInfo(name = ABILITY_NAME)
    var name: String = "",
    @ColumnInfo(name = ABILITY_VALUE)
    var value: Int = 10,
    @ColumnInfo(name = ABILITY_PROFICIENCY)
    var proficiency: Boolean = false
) {
    companion object {
        const val TABLE_ABILITY = "ability"
        const val ABILITY_CID = "cid"
        const val ABILITY_NAME = "name"
        const val ABILITY_VALUE = "value"
        const val ABILITY_PROFICIENCY = "proficiency"
    }

    fun getModifier() = if(value < 10) ((value - 11) / 2) else ((value - 10) / 2)

    fun getName(context: Context) = when(name) {
        STRENGTH -> context.getString(R.string.strength)
        DEXTERITY -> context.getString(R.string.dexterity)
        CONSTITUTION -> context.getString(R.string.constitution)
        INTELLIGENCE -> context.getString(R.string.intelligence)
        WISDOM -> context.getString(R.string.wisdom)
        CHARISMA -> context.getString(R.string.charisma)
        else -> ""
    }

    override fun toString(): String {
        return "[$cid] [" + (if(proficiency) "X" else " ") +  "] $name : $value"
    }
}