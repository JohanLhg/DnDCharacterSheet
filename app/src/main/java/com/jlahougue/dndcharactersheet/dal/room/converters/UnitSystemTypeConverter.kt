package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.UnitSystem

class UnitSystemTypeConverter {
    @TypeConverter
    fun toUnitSystem(value: String) = UnitSystem.from(value)

    @TypeConverter
    fun fromUnitSystem(value: UnitSystem) = value.name
}