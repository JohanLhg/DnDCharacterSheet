package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Notes.TABLE_NOTES)
class Notes(
    @PrimaryKey
    @ColumnInfo(name = NOTES_CID)
    var cid: Long = 0,
    @ColumnInfo(name = NOTES_CONTENT)
    var content: String = ""
) {
    companion object {
        const val TABLE_NOTES = "notes"
        const val NOTES_CID = "cid"
        const val NOTES_CONTENT = "content"
    }

    override fun toString(): String {
        return "[$cid] $content"
    }
}