package com.jlahougue.dndcharactersheet.dal.entities.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    """
        SELECT
            character.id AS cid,
            SUM(CASE WHEN spell.unlocked THEN 1 ELSE 0 END) as total_unlocked,
            SUM(CASE WHEN spell.prepared THEN 1 ELSE 0 END) as total_prepared,
            SUM(CASE WHEN spell.highlighted THEN 1 ELSE 0 END) as total_highlighted
        FROM character
        LEFT JOIN character_spell spell ON character.id = spell.cid
        GROUP BY character.id
    """,
    viewName = CharacterSpellStatsView.VIEW_CHARACTER_SPELL_STATS
)
class CharacterSpellStatsView(
    @ColumnInfo(name = CHAR_SPELL_STATS_CID)
    var cid: Long = 0,
    @ColumnInfo(name = CHAR_SPELL_STATS_TOTAL_UNLOCKED)
    var totalUnlocked: Int = 0,
    @ColumnInfo(name = CHAR_SPELL_STATS_TOTAL_PREPARED)
    var totalPrepared: Int = 0,
    @ColumnInfo(name = CHAR_SPELL_STATS_TOTAL_HIGHLIGHTED)
    var totalHighlighted: Int = 0
) {
    companion object {
        const val VIEW_CHARACTER_SPELL_STATS = "character_spell_stats_view"
        const val CHAR_SPELL_STATS_CID = "cid"
        const val CHAR_SPELL_STATS_TOTAL_UNLOCKED = "total_unlocked"
        const val CHAR_SPELL_STATS_TOTAL_PREPARED = "total_prepared"
        const val CHAR_SPELL_STATS_TOTAL_HIGHLIGHTED = "total_highlighted"
    }
}