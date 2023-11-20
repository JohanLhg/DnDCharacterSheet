package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.Language

class LanguageTypeConverter {
    @TypeConverter
    fun toLanguage(value: String) = Language.from(value)

    @TypeConverter
    fun fromLanguage(value: Language) = value.name
}