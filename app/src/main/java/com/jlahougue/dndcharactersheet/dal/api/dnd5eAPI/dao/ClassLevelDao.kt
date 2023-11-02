package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnDApiRequest
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnDApiRequest.Companion.getClassLevelsUrl
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.extensions.getIntIfExists
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ClassLevelDao {
    private val apiRequest = DnDApiRequest.getInstance()

    fun fetchClassLevels(
        clazz: String,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        save: (ClassLevel, List<ClassSpellSlot>) -> Unit
    ) {
        val response = apiRequest.sendGet(getClassLevelsUrl(clazz)) ?: return cancel()
        val json = JSONArray(response)
        setProgressMax(json.length())
        var classLevel: JSONObject
        for (i in 0 until json.length()) {
            CoroutineScope(Dispatchers.IO).launch {
                classLevel = json.getJSONObject(i)
                fetchClassLevel(clazz, classLevel, save)
            }
        }
    }

    private fun fetchClassLevel(
        clazz: String,
        jsonClassLevel: JSONObject,
        save: (ClassLevel, List<ClassSpellSlot>) -> Unit
    ) {
        val level = jsonClassLevel.getInt("level")
        val abilityScoreBonuses = jsonClassLevel.getInt("ability_score_bonuses")
        val profBonus = jsonClassLevel.getInt("prof_bonus")
        var cantripsKnown = 0
        var spellsKnown = 0
        var classSpellSlots = listOf<ClassSpellSlot>()
        if (jsonClassLevel.has("spellcasting")) {
            val spellcasting = jsonClassLevel.getJSONObject("spellcasting")
            cantripsKnown = spellcasting.getIntIfExists("cantrips_known")
            spellsKnown = spellcasting.getIntIfExists("spells_known")
            classSpellSlots = fetchClassSpellSlots(clazz, level, spellcasting)
        }

        val classLevel = ClassLevel(
            clazz,
            level,
            abilityScoreBonuses,
            profBonus,
            cantripsKnown,
            spellsKnown
        )

        save(classLevel, classSpellSlots)
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