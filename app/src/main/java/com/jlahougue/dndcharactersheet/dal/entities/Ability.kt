package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.jlahougue.dndcharactersheet.dal.entities.enums.AbilityName

@Entity(tableName = Ability.TABLE_ABILITY, primaryKeys = [Ability.ABILITY_CID, Ability.ABILITY_NAME])
class Ability(
    @ColumnInfo(name = ABILITY_CID)
    var cid: Long = 0,
    @ColumnInfo(name = ABILITY_NAME)
    var name: AbilityName = AbilityName.NONE,
    @ColumnInfo(name = ABILITY_VALUE)
    var value: Int = 10,
    @ColumnInfo(name = ABILITY_PROFICIENCY)
    var proficiency: Boolean = false
) {
    companion object {
        const val TABLE_ABILITY = "ability"
        const val ABILITY_CID = "cid"
        const val ABILITY_NAME = "name"
        const val ABILITY_VALUE = "value"
        const val ABILITY_PROFICIENCY = "proficiency"
    }

    override fun toString(): String {
        return "[$cid] [" + (if(proficiency) "X" else " ") +  "] $name : $value"
    }
}