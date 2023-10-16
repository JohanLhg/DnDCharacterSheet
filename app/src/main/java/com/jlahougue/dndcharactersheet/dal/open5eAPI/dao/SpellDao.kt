package com.jlahougue.dndcharactersheet.dal.open5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest.Companion.DND_API_SPELLS_URL
import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest.Companion.getUrl
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import org.json.JSONObject

class SpellDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchSpells(
        names: List<String>,
        saveSpell: (Spell) -> Unit,
        saveSpellClass: (SpellClass) -> Unit,
        saveSpellDamage: (SpellDamage) -> Unit,
        setProgress: (Int, Int) -> Unit,
        callback: () -> Unit
    ) {
        val response = apiRequest.sendGet(DND_API_SPELLS_URL) ?: return
        val json = JSONObject(response)
        setProgress(0, json.getInt("count"))
        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            name = results.getJSONObject(i).getString("name")
            url = results.getJSONObject(i).getString("url")
            if (!names.contains(name))
                fetchSpell(getUrl(url), saveSpell, saveSpellClass, saveSpellDamage)
            setProgress(i, json.getInt("count"))
        }
        callback()
    }

    private fun fetchSpell(
        url: String,
        saveSpell: (Spell) -> Unit,
        saveSpellClass: (SpellClass) -> Unit,
        saveSpellDamage: (SpellDamage) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return

        val json = JSONObject(response)
        val name = json.getString("name")
        val level = json.getInt("level")
        val castingTime = json.getString("casting_time")
        val range = json.getString("range")
        val duration = json.getString("duration")
        val ritual = json.getBoolean("ritual")
        val concentration = json.getBoolean("concentration")

        val spellComponents = json.getJSONArray("components")
        var components = ""
        for (i in 0 until spellComponents.length()) {
            if (i != 0) components += ", "
            components += spellComponents.getString(i)
        }

        var materials = ""
        if (json.has("material")) {
            materials = json.getString("material")
        }

        val spellDesc = json.getJSONArray("desc")
        var desc = ""
        for (i in 0 until spellDesc.length()) {
            if (i != 0) desc += "\n"
            desc += spellDesc.getString(i)
        }

        var higherLevel = ""
        if (json.has("higher_level")) {
            val spellHigherLevel = json.getJSONArray("higher_level")
            for (i in 0 until spellHigherLevel.length()) {
                if (i != 0) higherLevel += "\n"
                higherLevel += spellHigherLevel.getString(i)
            }
        }

        var damage: JSONObject? = null
        var damageDamageType = ""
        if (json.has("damage")) {
            damage = json.getJSONObject("damage")
            if (damage.has("damage_type")) {
                damageDamageType = damage.getJSONObject("damage_type").getString("name")
            }
        }

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
            damageDamageType
        ))

        val spellClasses = json.getJSONArray("classes")
        for (i in 0 until spellClasses.length()) {
            saveSpellClass(SpellClass(spellClasses.getJSONObject(i).getString("name"), name))
        }

        if (damage == null) return
        fetchSpellDamage(name, level, damage, saveSpellDamage)
    }

    private fun fetchSpellDamage(name: String, level: Int, jsonDamage: JSONObject, saveSpellDamage: (SpellDamage) -> Unit) {
        //If the spell's damage is based on the slot level
        if (jsonDamage.has("damage_at_slot_level")) {
            val spellDamageDamageAtSlotLevel = jsonDamage.getJSONObject("damage_at_slot_level")
            for (i in level until 9) {
                if (spellDamageDamageAtSlotLevel.has(i.toString())) {
                    saveSpellDamage(SpellDamage(name, i, 0, spellDamageDamageAtSlotLevel.getString(i.toString())))
                }
            }
            return
        }

        //If the spell's damage is based on the character level
        if (jsonDamage.has("damage_at_character_level")) {
            val spellDamageDamageAtCharacterLevel = jsonDamage.getJSONObject("damage_at_character_level")
            for (i in level until 20) {
                if (spellDamageDamageAtCharacterLevel.has(i.toString())) {
                    saveSpellDamage(SpellDamage(name, 0, i, spellDamageDamageAtCharacterLevel.getString(i.toString())))
                }
            }
        }
    }
}