package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.SkillName

class SkillNameTypeConverter {
    @TypeConverter
    fun toSkillName(value: String) = SkillName.from(value)

    @TypeConverter
    fun fromSkillName(value: SkillName) = value.name
}