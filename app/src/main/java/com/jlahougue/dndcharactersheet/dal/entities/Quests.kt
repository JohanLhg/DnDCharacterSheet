package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Quests.TABLE_QUESTS)
class Quests(
    @PrimaryKey
    @ColumnInfo(name = QUESTS_CID)
    var cid: Long = 0,
    @ColumnInfo(name = QUESTS_CONTENT)
    var content: String = ""
) {
    companion object {
        const val TABLE_QUESTS = "quests"
        const val QUESTS_CID = "cid"
        const val QUESTS_CONTENT = "content"
    }

    override fun toString(): String {
        return "[$cid] $content"
    }
}