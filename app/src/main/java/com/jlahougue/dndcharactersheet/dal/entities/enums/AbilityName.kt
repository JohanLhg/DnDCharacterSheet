package com.jlahougue.dndcharactersheet.dal.entities.enums

import android.content.Context
import com.jlahougue.dndcharactersheet.R

enum class AbilityName(
    val nameId: Int = R.string.empty,
    val shortNameId: Int = R.string.empty
) {
    STRENGTH(R.string.strength, R.string.modifier_strength),
    DEXTERITY(R.string.dexterity, R.string.modifier_dexterity),
    CONSTITUTION(R.string.constitution, R.string.modifier_constitution),
    INTELLIGENCE(R.string.intelligence, R.string.modifier_intelligence),
    WISDOM(R.string.wisdom, R.string.modifier_wisdom),
    CHARISMA(R.string.charisma, R.string.modifier_charisma),
    NONE(R.string.empty, R.string.empty);

    val code = name.substring(0, 3).uppercase()

    fun getName(context: Context) = context.getString(nameId)

    fun getShortName(context: Context) = context.getString(shortNameId)

    override fun toString() = code

    companion object {
        fun fromCode(findValue: String) = values().find { it.code.equals(findValue, true) } ?: NONE
        fun from(findValue: String) = values().find { it.name.equals(findValue, true) } ?: NONE

        // Ability codes (for compile time safety)
        const val STR = "STR"
        const val DEX = "DEX"
        const val CON = "CON"
        const val INT = "INT"
        const val WIS = "WIS"
        const val CHA = "CHA"
    }
}