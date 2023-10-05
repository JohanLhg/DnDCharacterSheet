package com.jlahougue.dndcharactersheet.dal.room.views

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository

@DatabaseView(
    """
        SELECT
            cid,
            name,
            CASE WHEN value < 10
                THEN (value - 11) / 2 
                ELSE (value - 10) / 2 
            END as modifier
        FROM ability
    """,
    viewName = AbilityModifierView.VIEW_ABILITY_MODIFIER
)
class AbilityModifierView(
    @ColumnInfo(name = ABILITY_MODIFIER_CID)
    var cid: Long,
    @ColumnInfo(name = ABILITY_MODIFIER_NAME)
    var name: String,
    @ColumnInfo(name = ABILITY_MODIFIER_MODIFIER)
    var modifier: Int
) {
    companion object {
        const val VIEW_ABILITY_MODIFIER = "ability_modifier_view"
        const val ABILITY_MODIFIER_CID = "cid"
        const val ABILITY_MODIFIER_NAME = "name"
        const val ABILITY_MODIFIER_MODIFIER = "modifier"
    }
}