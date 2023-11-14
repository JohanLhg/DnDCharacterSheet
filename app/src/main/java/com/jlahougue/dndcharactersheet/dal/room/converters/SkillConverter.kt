package com.jlahougue.dndcharactersheet.dal.room.converters

import androidx.room.TypeConverter
import com.jlahougue.dndcharactersheet.dal.entities.enums.SkillName

class SkillConverter {
    @TypeConverter
    fun fromSkill(value: SkillName): String {
        return value.name
    }

    @TypeConverter
    fun toSkill(value: String): SkillName {
        return SkillName.valueOf(value)
    }
}