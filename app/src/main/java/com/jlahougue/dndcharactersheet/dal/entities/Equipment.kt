package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Equipment.TABLE_EQUIPMENT)
class Equipment(
    @PrimaryKey
    @ColumnInfo(name = EQUIPMENT_CID)
    var cid: Long = 0,
    @ColumnInfo(name = EQUIPMENT_CONTENT)
    var content: String = ""
) {
    companion object {
        const val TABLE_EQUIPMENT = "equipment"
        const val EQUIPMENT_CID = "cid"
        const val EQUIPMENT_CONTENT = "content"
    }

    override fun toString(): String {
        return "[$cid] $content"
    }
}