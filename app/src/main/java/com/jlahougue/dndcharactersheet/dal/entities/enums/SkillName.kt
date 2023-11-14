package com.jlahougue.dndcharactersheet.dal.entities.enums

import android.content.Context
import com.jlahougue.dndcharactersheet.R

enum class SkillName {
    ACROBATICS {
        override fun getName(context: Context) = context.getString(R.string.acrobatics)
    },
    ANIMAL_HANDLING {
        override fun getName(context: Context) = context.getString(R.string.animal_handling)
    },
    ARCANA {
        override fun getName(context: Context) = context.getString(R.string.arcana)
    },
    ATHLETICS {
        override fun getName(context: Context) = context.getString(R.string.athletics)
    },
    DECEPTION {
        override fun getName(context: Context) = context.getString(R.string.deception)
    },
    HISTORY {
        override fun getName(context: Context) = context.getString(R.string.history)
    },
    INSIGHT {
        override fun getName(context: Context) = context.getString(R.string.insight)
    },
    INTIMIDATION {
        override fun getName(context: Context) = context.getString(R.string.intimidation)
    },
    INVESTIGATION {
        override fun getName(context: Context) = context.getString(R.string.investigation)
    },
    MEDICINE {
        override fun getName(context: Context) = context.getString(R.string.medicine)
    },
    NATURE {
        override fun getName(context: Context) = context.getString(R.string.nature)
    },
    PERCEPTION {
        override fun getName(context: Context) = context.getString(R.string.perception)
    },
    PERFORMANCE {
        override fun getName(context: Context) = context.getString(R.string.performance)
    },
    PERSUASION {
        override fun getName(context: Context) = context.getString(R.string.persuasion)
    },
    RELIGION {
        override fun getName(context: Context) = context.getString(R.string.religion)
    },
    SLEIGHT_OF_HAND {
        override fun getName(context: Context) = context.getString(R.string.sleight_of_hand)
    },
    STEALTH {
        override fun getName(context: Context) = context.getString(R.string.stealth)
    },
    SURVIVAL {
        override fun getName(context: Context) = context.getString(R.string.survival)
    };

    abstract fun getName(context: Context): String
}