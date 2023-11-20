package com.jlahougue.dndcharactersheet.dal.entities.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName

@DatabaseView(
    """
        SELECT
            ability.cid,
            ability.name,
            ability.value,
            modifier.modifier as base_modifier,
            CASE WHEN proficiency
                THEN modifier.modifier + proficiency.bonus
                ELSE modifier.modifier
            END as modifier,
            ability.proficiency
        FROM ability
        LEFT JOIN ability_modifier_view modifier ON ability.cid = modifier.cid AND ability.name = modifier.name
        LEFT JOIN proficiency_view proficiency ON ability.cid = proficiency.cid
    """,
    viewName = AbilityView.VIEW_ABILITY
)
class AbilityView(
    @ColumnInfo(name = ABILITY_CID)
    var cid: Long,
    @ColumnInfo(name = ABILITY_NAME)
    var name: AbilityName,
    @ColumnInfo(name = ABILITY_VALUE)
    var value: Int,
    @ColumnInfo(name = ABILITY_BASE_MODIFIER)
    var baseModifier: Int,
    @ColumnInfo(name = ABILITY_MODIFIER)
    var modifier: Int,
    @ColumnInfo(name = ABILITY_PROFICIENCY)
    var proficiency: Boolean
) {
    companion object {
        const val VIEW_ABILITY = "ability_view"
        const val ABILITY_CID = "cid"
        const val ABILITY_NAME = "name"
        const val ABILITY_VALUE = "value"
        const val ABILITY_BASE_MODIFIER = "base_modifier"
        const val ABILITY_MODIFIER = "modifier"
        const val ABILITY_PROFICIENCY = "proficiency"
    }
}