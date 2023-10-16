package com.jlahougue.dndcharactersheet.dal.dndAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest.Companion.getClassLevelsUrl
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class ClassLevelDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchClassLevels(
        clazz: String,
        saveClassLevel: (ClassLevel) -> Unit,
        saveClassSpellSlot: (ClassSpellSlot) -> Unit
    ) {
        val response = apiRequest.sendGet(getClassLevelsUrl(clazz)) ?: return
        val json = JSONArray(response)
        var classLevel: JSONObject
        for (i in 0 until json.length()) {
            thread {
                classLevel = json.getJSONObject(i)
                fetchClassLevel(clazz, classLevel, saveClassLevel, saveClassSpellSlot)
            }
        }
    }

    private fun fetchClassLevel(
        clazz: String,
        classLevel: JSONObject,
        saveClassLevel: (ClassLevel) -> Unit,
        saveClassSpellSlot: (ClassSpellSlot) -> Unit,
    ) {
        val level = classLevel.getInt("level")
        val abilityScoreBonuses = classLevel.getInt("ability_score_bonuses")
        val profBonus = classLevel.getInt("prof_bonus")
        var cantripsKnown = 0
        var spellsKnown = 0
        if (classLevel.has("spellcasting")) {
            val spellcasting = classLevel.getJSONObject("spellcasting")
            if (spellcasting.has("cantrips_known"))
                cantripsKnown = spellcasting.getInt("cantrips_known")
            if (spellcasting.has("spells_known"))
                spellsKnown = spellcasting.getInt("spells_known")
            fetchClassSpellSlots(clazz, level, spellcasting, saveClassSpellSlot)
        }

        saveClassLevel(ClassLevel(clazz, level, abilityScoreBonuses, profBonus, cantripsKnown, spellsKnown))
    }

    private fun fetchClassSpellSlots(
        clazz: String,
        level: Int,
        spellcasting: JSONObject,
        saveClassSpellSlot: (ClassSpellSlot) -> Unit
    ) {
        var slotLevel = 1
        while (spellcasting.has("spell_slots_level_$slotLevel")) {
            val spellSlots = spellcasting.getInt("spell_slots_level_$slotLevel")
            saveClassSpellSlot(ClassSpellSlot(clazz, level, slotLevel, spellSlots))
            slotLevel++
        }
    }
}