package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.INTELLIGENCE

@Entity(tableName = Spellcasting.TABLE_SPELLCASTING)
class Spellcasting(
    @PrimaryKey
    @ColumnInfo(name = SPELLCASTING_CID)
    var cid: Long = 0,
    @ColumnInfo(name = SPELLCASTING_ABILITY)
    var ability: String = INTELLIGENCE
) {
    companion object {
        const val TABLE_SPELLCASTING = "spellcasting"
        const val SPELLCASTING_CID = "cid"
        const val SPELLCASTING_ABILITY = "ability"
    }
}