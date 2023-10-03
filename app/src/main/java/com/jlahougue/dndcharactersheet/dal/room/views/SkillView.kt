package com.jlahougue.dndcharactersheet.dal.room.views

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.dal.repositories.SkillRepository

@DatabaseView(
    """
       SELECT
           skill.cid,
           skill.name,
           skill.modifier_type,
           CASE WHEN skill.proficiency 
               THEN ability.modifier + proficiency.bonus
               ELSE ability.modifier
           END as modifier,
           skill.proficiency
       FROM skill
       LEFT JOIN ability_modifier_view ability ON skill.cid = ability.cid AND skill.modifier_type = ability.name
       LEFT JOIN proficiency_view proficiency ON ability.cid = proficiency.cid
    """,
    viewName = SkillView.VIEW_SKILL
)
class SkillView(
    @ColumnInfo(name = SKILL_CID)
    var cid: Long,
    @ColumnInfo(name = SKILL_NAME)
    var name: String,
    @ColumnInfo(name = SKILL_MODIFIER_TYPE)
    var modifierType: String,
    @ColumnInfo(name = SKILL_MODIFIER)
    var modifier: Int,
    @ColumnInfo(name = SKILL_PROFICIENCY)
    var proficiency: Boolean
) {
    companion object {
        const val VIEW_SKILL = "skill_view"
        const val SKILL_CID = "cid"
        const val SKILL_NAME = "name"
        const val SKILL_MODIFIER_TYPE = "modifier_type"
        const val SKILL_MODIFIER = "modifier"
        const val SKILL_PROFICIENCY = "proficiency"
    }

    private fun getName(context: Context) = when(name) {
        SkillRepository.ACROBATICS -> context.getString(R.string.acrobatics)
        SkillRepository.ANIMAL_HANDLING -> context.getString(R.string.animal_handling)
        SkillRepository.ARCANA -> context.getString(R.string.arcana)
        SkillRepository.ATHLETICS -> context.getString(R.string.athletics)
        SkillRepository.DECEPTION -> context.getString(R.string.deception)
        SkillRepository.HISTORY -> context.getString(R.string.history)
        SkillRepository.INSIGHT -> context.getString(R.string.insight)
        SkillRepository.INTIMIDATION -> context.getString(R.string.intimidation)
        SkillRepository.INVESTIGATION -> context.getString(R.string.investigation)
        SkillRepository.MEDICINE -> context.getString(R.string.medicine)
        SkillRepository.NATURE -> context.getString(R.string.nature)
        SkillRepository.PERCEPTION -> context.getString(R.string.perception)
        SkillRepository.PERFORMANCE -> context.getString(R.string.performance)
        SkillRepository.PERSUASION -> context.getString(R.string.persuasion)
        SkillRepository.RELIGION -> context.getString(R.string.religion)
        SkillRepository.SLEIGHT_OF_HAND -> context.getString(R.string.sleight_of_hand)
        SkillRepository.STEALTH -> context.getString(R.string.stealth)
        SkillRepository.SURVIVAL -> context.getString(R.string.survival)
        else -> ""
    }

    private fun getAbilityName(context: Context) = when(modifierType) {
        AbilityRepository.STRENGTH -> context.getString(R.string.modifier_strength)
        AbilityRepository.DEXTERITY -> context.getString(R.string.modifier_dexterity)
        AbilityRepository.CONSTITUTION -> context.getString(R.string.modifier_constitution)
        AbilityRepository.INTELLIGENCE -> context.getString(R.string.modifier_intelligence)
        AbilityRepository.WISDOM -> context.getString(R.string.modifier_wisdom)
        AbilityRepository.CHARISMA -> context.getString(R.string.modifier_charisma)
        else -> ""
    }

    fun getFullName(context: Context) = "${getName(context)} (${getAbilityName(context)})"
}