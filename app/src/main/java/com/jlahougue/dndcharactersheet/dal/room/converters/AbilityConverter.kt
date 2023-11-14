package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName

class AbilityConverter {
    @TypeConverter
    fun fromAbility(value: AbilityName): String {
        return value.name
    }

    @TypeConverter
    fun toAbility(value: String): AbilityName {
        return AbilityName.valueOf(value)
    }
}