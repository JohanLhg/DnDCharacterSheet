package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.jlahougue.dndcharactersheet.dal.entities.Skill
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase

class SkillDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun save(skill: Skill) {
        firebaseDatabase.updateCharacterSheet(skill.cid, mapOf("skills.${skill.name}" to skill))
    }
}