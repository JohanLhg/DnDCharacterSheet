package com.jlahougue.dndcharactersheet.dal.entities.displayClasses

import androidx.room.ColumnInfo

class AttackStats(
    @ColumnInfo(name = PROFICIENCY_BONUS)
    val proficiencyBonus: Int,
    @ColumnInfo(name = MELEE_MODIFIER)
    val meleeModifier: Int,
    @ColumnInfo(name = RANGED_MODIFIER)
    val rangedModifier: Int,
) {
    companion object {
        const val PROFICIENCY_BONUS = "proficiency_bonus"
        const val MELEE_MODIFIER = "melee_modifier"
        const val RANGED_MODIFIER = "ranged_modifier"
    }
}