package com.jlahougue.dndcharactersheet.dal.api.open5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest.Companion.SPELLS_CHECK_URL
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest.Companion.SPELLS_URL
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.json.JSONObject

class SpellDao {
    private val apiRequest = Open5eApiRequest.getInstance()

    suspend fun fetchSpells(
        ids: List<String>,
        damageTypes: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        saveSpell: (Spell) -> Long,
        saveClasses: (List<SpellClass>) -> Unit,
        saveDamages: (List<SpellDamage>) -> Unit
    ) {
        var response = apiRequest.sendGet(SPELLS_CHECK_URL) ?: return cancel()
        var json = JSONObject(response)
        val count = json.getInt("count")
        if (count == ids.size) return cancel()
        setProgressMax(count)

        response = apiRequest.sendGet(SPELLS_URL) ?: return cancel()
        json = JSONObject(response)
        val results = json.getJSONArray("results")

        (0..<results.length()).map {
            CoroutineScope(Dispatchers.IO).launch {
                val spell = results.getJSONObject(it)
                val id = spell.getString("slug")

                if (ids.contains(id)) skip()
                else fetchSpell(
                    spell,
                    damageTypes,
                    skip,
                    updateProgress,
                    saveSpell,
                    saveClasses,
                    saveDamages
                )
            }
        }.joinAll()
    }

    private fun fetchSpell(
        jsonSpell: JSONObject,
        damageTypes: List<String>,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        saveSpell: (Spell) -> Long,
        saveClasses: (List<SpellClass>) -> Unit,
        saveDamages: (List<SpellDamage>) -> Unit
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

        val failed = saveSpell(
            Spell(
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
        ) == -1L

        if (failed) return skip()

        //Classes
        val classes = mutableListOf<SpellClass>()
        val spellClasses = jsonSpell.getString("dnd_class").split(", ")
        for (clazz in spellClasses) {
            if (clazz.isBlank()) continue
            classes.add(SpellClass(id, clazz))
        }
        saveClasses(classes)

        updateProgress()
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