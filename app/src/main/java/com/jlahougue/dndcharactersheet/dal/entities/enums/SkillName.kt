package com.jlahougue.dndcharactersheet.dal.entities.enums

import android.content.Context
import com.jlahougue.dndcharactersheet.R

enum class SkillName(val nameId: Int) {
    ACROBATICS(R.string.acrobatics),
    ANIMAL_HANDLING(R.string.animal_handling),
    ARCANA(R.string.arcana),
    ATHLETICS(R.string.athletics),
    DECEPTION(R.string.deception),
    HISTORY(R.string.history),
    INSIGHT(R.string.insight),
    INTIMIDATION(R.string.intimidation),
    INVESTIGATION(R.string.investigation),
    MEDICINE(R.string.medicine),
    NATURE(R.string.nature),
    PERCEPTION(R.string.perception),
    PERFORMANCE(R.string.performance),
    PERSUASION(R.string.persuasion),
    RELIGION(R.string.religion),
    SLEIGHT_OF_HAND(R.string.sleight_of_hand),
    STEALTH(R.string.stealth),
    SURVIVAL(R.string.survival),
    NONE(R.string.empty);

    fun getName(context: Context) = context.getString(nameId)

    companion object {
        fun from(findValue: String) = values().find { it.name.equals(findValue, true) } ?: NONE
    }
}