package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.LANGUAGE_EN
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.LANGUAGE_FR
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.UNIT_SYSTEM_IMPERIAL
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.UNIT_SYSTEM_METRIC

@Entity(tableName = Preferences.TABLE_PREFERENCES)
class Preferences(
    @PrimaryKey
    @ColumnInfo(name = PREFERENCES_ID)
    var id: Long = 1L,
    @ColumnInfo(name = PREFERENCES_LANGUAGE)
    var language: String = LANGUAGE_EN,
    @ColumnInfo(name = PREFERENCES_UNIT_SYSTEM)
    var unitSystem: String = UNIT_SYSTEM_METRIC
) {
    fun isEnglish() = language == LANGUAGE_EN

    fun isFrench() = language == LANGUAGE_FR

    fun isMetric() = unitSystem == UNIT_SYSTEM_METRIC

    fun isImperial() = unitSystem == UNIT_SYSTEM_IMPERIAL

    companion object {
        const val TABLE_PREFERENCES = "preferences"
        const val PREFERENCES_ID = "id"
        const val PREFERENCES_LANGUAGE = "language"
        const val PREFERENCES_UNIT_SYSTEM = "unit_system"
    }
}