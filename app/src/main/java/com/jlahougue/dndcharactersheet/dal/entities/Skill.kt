package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName
import com.jlahougue.dndcharactersheet.dal.entities.enums.SkillName

@Entity(tableName = Skill.TABLE_SKILL, primaryKeys = [Skill.SKILL_CID, Skill.SKILL_NAME])
class Skill(
    @ColumnInfo(name = SKILL_CID)
    var cid: Long = 0,
    @ColumnInfo(name = SKILL_NAME)
    var name: SkillName,
    @ColumnInfo(name = SKILL_MODIFIER_TYPE)
    var modifierType: AbilityName,
    @ColumnInfo(name = SKILL_PROFICIENCY)
    var proficiency: Boolean = false
) {
    companion object {
        const val TABLE_SKILL = "skill"
        const val SKILL_CID = "cid"
        const val SKILL_NAME = "name"
        const val SKILL_MODIFIER_TYPE = "modifier_type"
        const val SKILL_PROFICIENCY = "proficiency"
    }

    override fun toString(): String {
        return "[$cid] [" + (if(proficiency) "X" else " ") +  "] $name ($modifierType)"
    }
}