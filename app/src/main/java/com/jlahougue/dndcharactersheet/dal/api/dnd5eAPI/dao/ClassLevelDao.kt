package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest.Companion.getClassLevelsUrl
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.extensions.getIntIfExists
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ClassLevelDao {
    private val apiRequest = DnD5eApiRequest.getInstance()

    suspend fun fetchClassLevels(
        clazz: String,
        saveLevel: (ClassLevel) -> Long,
        saveSpellSlots: (List<ClassSpellSlot>) -> Unit
    ) {
        val response = apiRequest.sendGet(getClassLevelsUrl(clazz)) ?: return
        val json = JSONArray(response)
        var classLevel: JSONObject
        (0..<json.length()).map {
            CoroutineScope(Dispatchers.IO).launch {
                classLevel = json.getJSONObject(it)
                fetchClassLevel(clazz, classLevel, saveLevel, saveSpellSlots)
            }
        }.joinAll()
    }

    private fun fetchClassLevel(
        clazz: String,
        jsonClassLevel: JSONObject,
        saveLevel: (ClassLevel) -> Long,
        saveSpellSlots: (List<ClassSpellSlot>) -> Unit
    ) {
        val level = jsonClassLevel.getInt("level")
        val abilityScoreBonuses = jsonClassLevel.getInt("ability_score_bonuses")
        val profBonus = jsonClassLevel.getInt("prof_bonus")

        //Spells
        var cantripsKnown = 0
        var spellsKnown = 0
        var classSpellSlots = listOf<ClassSpellSlot>()
        if (jsonClassLevel.has("spellcasting")) {
            val spellcasting = jsonClassLevel.getJSONObject("spellcasting")
            cantripsKnown = spellcasting.getIntIfExists("cantrips_known")
            spellsKnown = spellcasting.getIntIfExists("spells_known")
            classSpellSlots = fetchClassSpellSlots(clazz, level, spellcasting)
        }

        val failed = saveLevel(
            ClassLevel(
            clazz,
            level,
            abilityScoreBonuses,
            profBonus,
            cantripsKnown,
            spellsKnown
        )
        ) == -1L

        if (failed) return

        saveSpellSlots(classSpellSlots)
    }

    private fun fetchClassSpellSlots(
        clazz: String,
        level: Int,
        spellcasting: JSONObject
    ): List<ClassSpellSlot> {
        val classSpellSlots = mutableListOf<ClassSpellSlot>()
        var slotLevel = 1
        while (spellcasting.has("spell_slots_level_$slotLevel")) {
            val spellSlots = spellcasting.getInt("spell_slots_level_$slotLevel")
            classSpellSlots.add(ClassSpellSlot(clazz, level, slotLevel, spellSlots))
            slotLevel++
        }
        return classSpellSlots
    }
}