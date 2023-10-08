package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = SpellSlotUses.TABLE_SPELL_SLOT_USES,
    primaryKeys = [SpellSlotUses.SPELL_SLOT_USES_CID, SpellSlotUses.SPELL_SLOT_USES_LEVEL]
)
class SpellSlotUses(
    @ColumnInfo(name = SPELL_SLOT_USES_CID)
    val cid: Long,
    @ColumnInfo(name = SPELL_SLOT_USES_LEVEL)
    val level: Int,
    @ColumnInfo(name = SPELL_SLOT_USES_SLOTS_USED)
    val slotsUsed: Int
) {
    companion object {
        const val TABLE_SPELL_SLOT_USES = "spell_slot_uses"
        const val SPELL_SLOT_USES_CID = "cid"
        const val SPELL_SLOT_USES_LEVEL = "level"
        const val SPELL_SLOT_USES_SLOTS_USED = "slots_used"
    }
}