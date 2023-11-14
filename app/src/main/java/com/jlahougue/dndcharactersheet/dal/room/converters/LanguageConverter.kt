package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.Language

class LanguageConverter {
    @TypeConverter
    fun fromLanguage(value: Language): String {
        return value.name
    }

    @TypeConverter
    fun toLanguage(value: String): Language {
        return Language.valueOf(value)
    }
}