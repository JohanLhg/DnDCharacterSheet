package com.jlahougue.dndcharactersheet.dal.entities.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    """
        SELECT spellcasting.cid,
        spellcasting.ability,
        ability.base_modifier AS modifier,
        8 + ability.base_modifier + proficiency.bonus AS save_dc,
        ability.base_modifier + proficiency.bonus AS attack_bonus
        FROM spellcasting
        INNER JOIN ability_view AS ability
        ON spellcasting.ability = ability.name
        INNER JOIN proficiency_view AS proficiency
        ON spellcasting.cid = proficiency.cid
    """,
    viewName = SpellcastingView.VIEW_SPELLCASTING
)
class SpellcastingView(
    @ColumnInfo(name = SPELLCASTING_CID)
    var cid: Long = 0,
    @ColumnInfo(name = SPELLCASTING_ABILITY)
    var ability: String = "",
    @ColumnInfo(name = SPELLCASTING_MODIFIER)
    var modifier: Int = 0,
    @ColumnInfo(name = SPELLCASTING_SAVE_DC)
    var saveDC: Int = 0,
    @ColumnInfo(name = SPELLCASTING_ATTACK_BONUS)
    var attackBonus: Int = 0
) {
    companion object {
        const val VIEW_SPELLCASTING = "spellcasting_view"
        const val SPELLCASTING_CID = "cid"
        const val SPELLCASTING_ABILITY = "ability"
        const val SPELLCASTING_MODIFIER = "modifier"
        const val SPELLCASTING_SAVE_DC = "save_dc"
        const val SPELLCASTING_ATTACK_BONUS = "attack_bonus"

    }
}