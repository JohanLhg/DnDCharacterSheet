package com.jlahougue.dndcharactersheet.dal.api.open5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest.Companion.SPELLS_CHECK_URL
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest.Companion.SPELLS_URL
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class SpellDao {
    private val apiRequest = Open5eApiRequest.getInstance()

    fun fetchSpells(
        ids: List<String>,
        damageTypes: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Spell, List<SpellClass>, List<SpellDamage>) -> Unit
    ) {
        var response = apiRequest.sendGet(SPELLS_CHECK_URL) ?: return cancel()
        var json = JSONObject(response)
        val count = json.getInt("count")
        if (count == ids.size) return cancel()
        setProgressMax(count)

        response = apiRequest.sendGet(SPELLS_URL) ?: return cancel()
        json = JSONObject(response)
        val results = json.getJSONArray("results")

        var id: String
        var spell: JSONObject
        for (i in 0 until results.length()) {
            CoroutineScope(Dispatchers.IO).launch {
                spell = results.getJSONObject(i)
                id = spell.getString("slug")
                if (ids.contains(id)) skip()
                else fetchSpell(spell, damageTypes, save)
            }
        }
    }

    private fun fetchSpell(
        jsonSpell: JSONObject,
        damageTypes: List<String>,
        save: (Spell, List<SpellClass>, List<SpellDamage>) -> Unit,
    ) {
        val id = jsonSpell.getString("slug")
        val name = jsonSpell.getString("name").replace("/", " - ")
        val level = jsonSpell.getInt("level_int")
        val castingTime = jsonSpell.getString("casting_time")
        val range = jsonSpell.getString("range")
        val duration = jsonSpell.getString("duration")
        val ritual = jsonSpell.getBoolean("can_be_cast_as_ritual")
        val concentration = jsonSpell.getBoolean("requires_concentration")

        val components = jsonSpell.getString("components")
        val materials = jsonSpell.getString("material")

        val desc = jsonSpell.getString("desc")
        val higherLevel = jsonSpell.getString("higher_level")

        val damageType = getDamageType(damageTypes, desc)

        val spell = Spell(
            id,
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
            damageType
        )

        val classes = mutableListOf<SpellClass>()
        val spellClasses = jsonSpell.getString("dnd_class").split(", ")
        for (clazz in spellClasses) {
            if (clazz.isBlank()) continue
            classes.add(SpellClass(id, clazz))
        }

        val damages = mutableListOf<SpellDamage>()

        save(spell, classes, damages)
    }

    private fun getDamageType(damageTypes: List<String>, description: String): String {
        val spellDamageTypes = listOf<String>()
        for (damageType in damageTypes) {
            if (
                description.contains("$damageType damage",  true)
                || description.contains("damage $damageType",  true)
            ) {
                spellDamageTypes.plus(damageType)
                break
            }
        }
        return spellDamageTypes.joinToString(", ")
    }
}