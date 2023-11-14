package com.jlahougue.dndcharactersheet.dal.entities.enums

import android.content.Context
import com.jlahougue.dndcharactersheet.R

enum class AbilityName {
    STRENGTH {
        override fun getName(context: Context) = context.getString(R.string.strength)
        override fun getShortName(context: Context) = context.getString(R.string.modifier_strength)
    },
    DEXTERITY {
        override fun getName(context: Context) = context.getString(R.string.dexterity)
        override fun getShortName(context: Context) = context.getString(R.string.modifier_dexterity)
    },
    CONSTITUTION {
        override fun getName(context: Context) = context.getString(R.string.constitution)
        override fun getShortName(context: Context) = context.getString(R.string.modifier_constitution)
    },
    INTELLIGENCE {
        override fun getName(context: Context) = context.getString(R.string.intelligence)
        override fun getShortName(context: Context) = context.getString(R.string.modifier_intelligence)
    },
    WISDOM {
        override fun getName(context: Context) = context.getString(R.string.wisdom)
        override fun getShortName(context: Context) = context.getString(R.string.modifier_wisdom)
    },
    CHARISMA {
        override fun getName(context: Context) = context.getString(R.string.charisma)
        override fun getShortName(context: Context) = context.getString(R.string.modifier_charisma)
    };

    abstract fun getName(context: Context): String
    abstract fun getShortName(context: Context): String
}