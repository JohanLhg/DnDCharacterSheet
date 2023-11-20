package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jlahougue.dndcharactersheet.dal.entities.enums.Language
import com.jlahougue.dndcharactersheet.dal.entities.enums.UnitSystem

@Entity(tableName = Preferences.TABLE_PREFERENCES)
class Preferences(
    @PrimaryKey
    @ColumnInfo(name = PREFERENCES_ID)
    var id: Long = 1L,
    @ColumnInfo(name = PREFERENCES_LANGUAGE)
    var language: Language = Language.EN,
    @ColumnInfo(name = PREFERENCES_UNIT_SYSTEM)
    var unitSystem: UnitSystem = UnitSystem.METRIC
) {
    fun isEnglish() = language == Language.EN

    fun isFrench() = language == Language.FR

    fun isMetric() = unitSystem == UnitSystem.METRIC

    fun isImperial() = unitSystem == UnitSystem.IMPERIAL

    companion object {
        const val TABLE_PREFERENCES = "preferences"
        const val PREFERENCES_ID = "id"
        const val PREFERENCES_LANGUAGE = "language"
        const val PREFERENCES_UNIT_SYSTEM = "unit_system"
    }
}