package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Property.TABLE_PROPERTY)
class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PROPERTY_ID)
    var id: Long = 0,
    @ColumnInfo(name = PROPERTY_NAME)
    var name: String = "",
    @ColumnInfo(name = PROPERTY_DESCRIPTION)
    var description: String = ""
) {
    companion object {
        const val TABLE_PROPERTY = "property"
        const val PROPERTY_ID = "id"
        const val PROPERTY_NAME = "name"
        const val PROPERTY_DESCRIPTION = "description"
    }
}