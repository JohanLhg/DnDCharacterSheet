package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.UnitSystem

class UnitSystemConverter {
    @TypeConverter
    fun fromUnitSystem(value: UnitSystem): String {
        return value.name
    }

    @TypeConverter
    fun toUnitSystem(value: String): UnitSystem {
        return UnitSystem.valueOf(value)
    }
}