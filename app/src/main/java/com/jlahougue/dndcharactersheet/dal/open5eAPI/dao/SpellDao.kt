package com.jlahougue.dndcharactersheet.dal.open5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.open5eAPI.Open5eAPIRequest
import com.jlahougue.dndcharactersheet.dal.open5eAPI.Open5eAPIRequest.Companion.OPEN5E_API_SPELLS_URL
import org.json.JSONObject
import kotlin.concurrent.thread

class SpellDao {
    private val apiRequest = Open5eAPIRequest.getInstance()

    fun fetchSpells(
        names: List<String>,
        saveSpell: (Spell) -> Unit,
        saveSpellClass: (SpellClass) -> Unit,
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val response = apiRequest.sendGet(OPEN5E_API_SPELLS_URL) ?: return
        val json = JSONObject(response)
        setProgressMax(progressKey, json.getInt("count"))
        val results = json.getJSONArray("results")
        var name: String
        var spell: JSONObject
        for (i in 0 until results.length()) {
            thread {
                spell = results.getJSONObject(i)
                name = spell.getString("name").replace("/", " - ")
                if (!names.contains(name))
                    fetchSpell(spell, saveSpell, saveSpellClass)
                updateProgress(progressKey)
            }
        }
    }

    private fun fetchSpell(
        spell: JSONObject,
        saveSpell: (Spell) -> Unit,
        saveSpellClass: (SpellClass) -> Unit
    ) {
        val name = spell.getString("name").replace("/", " - ")
        val level = spell.getInt("level_int")
        val castingTime = spell.getString("casting_time")
        val range = spell.getString("range")
        val duration = spell.getString("duration")
        val ritual = spell.getBoolean("can_be_cast_as_ritual")
        val concentration = spell.getBoolean("requires_concentration")

        val components = spell.getString("components")
        val materials = spell.getString("material")

        val desc = spell.getString("desc")
        val higherLevel = spell.getString("higher_level")

        saveSpell(Spell(
            name,
            level,
            castingTime,
            range,
            components,
            materials,
            ritual,
            concentration,
            duration,
            desc,
            higherLevel,
            ""
        ))

        val spellClasses = spell.getString("dnd_class").split(", ")
        for (clazz in spellClasses) {
            if (clazz.isBlank()) continue
            saveSpellClass(SpellClass(name, clazz))
        }
    }
}