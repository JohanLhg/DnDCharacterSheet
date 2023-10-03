package com.jlahougue.dndcharactersheet.dal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlahougue.dndcharactersheet.dal.entities.Skill
import com.jlahougue.dndcharactersheet.dal.entities.Skill.Companion.SKILL_NAME
import com.jlahougue.dndcharactersheet.dal.room.views.SkillView

@Dao
interface SkillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(skill: Skill)

    @Update
    fun update(skill: Skill)

    @Delete
    fun delete(skill: Skill)

    @Query("SELECT * FROM skill_view WHERE cid = :characterID")
    fun get(characterID: Long): LiveData<List<SkillView>>

    @MapInfo(keyColumn = SKILL_NAME)
    @Query("SELECT * FROM skill WHERE cid = :characterID")
    fun getMap(characterID: Long): Map<String, Skill>
}