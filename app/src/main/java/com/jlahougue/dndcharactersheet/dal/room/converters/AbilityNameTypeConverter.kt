package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName

class AbilityNameTypeConverter {
    @TypeConverter
    fun toAbilityName(value: String) = AbilityName.fromCode(value)

    @TypeConverter
    fun fromAbilityName(value: AbilityName) = value.code
}