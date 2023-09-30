package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Skill

@Dao
interface SkillDao {
    @Insert
    fun insert(skill: Skill)

    @Update
    fun update(skill: Skill)

    @Delete
    fun delete(skill: Skill)
}