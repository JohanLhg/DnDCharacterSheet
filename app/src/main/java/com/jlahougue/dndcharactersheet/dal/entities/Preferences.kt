package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Preferences.TABLE_PREFERENCES)
class Preferences(
    @PrimaryKey
    @ColumnInfo(name = PREFERENCES_ID)
    var id: Long = 1L,
    @ColumnInfo(name = PREFERENCES_LANGUAGE)
    var language: String = "en"
) {
    companion object {
        const val TABLE_PREFERENCES = "preferences"
        const val PREFERENCES_ID = "id"
        const val PREFERENCES_LANGUAGE = "language"
    }
}