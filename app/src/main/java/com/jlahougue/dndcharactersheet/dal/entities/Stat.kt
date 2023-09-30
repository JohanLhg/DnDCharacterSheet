package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = Stat.TABLE_STAT, primaryKeys = [Stat.STAT_CID, Stat.STAT_NAME])
class Stat(
    @ColumnInfo(name = STAT_CID)
    var cid: Long = 0,
    @ColumnInfo(name = STAT_NAME)
    var name: String = "",
    @ColumnInfo(name = STAT_VALUE)
    var value: Int = 0
) {
    companion object {
        const val TABLE_STAT = "stat"
        const val STAT_CID = "cid"
        const val STAT_NAME = "name"
        const val STAT_VALUE = "value"
    }

    override fun toString(): String {
        return "[$cid] $name : $value"
    }
}