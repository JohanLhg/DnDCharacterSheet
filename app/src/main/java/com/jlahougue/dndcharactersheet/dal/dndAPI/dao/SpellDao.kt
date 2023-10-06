package com.jlahougue.dndcharactersheet.dal.dndAPI.dao

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
        callback: () -> Unit
    ) {
        val response = apiRequest.sendGet(DND_API_SPELLS_URL) ?: return
        val json = JSONObject(response)
        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            name = results.getJSONObject(i).getString("name")
            url = results.getJSONObject(i).getString("url")
            if (names.contains(name)) continue
            fetchSpell(getUrl(url), saveSpell, saveSpellClass, saveSpellDamage)
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
        val spellName = json.getString("name")
        val spellLevel = json.getInt("level")
        val spellCastingTime = json.getString("casting_time")
        val spellRange = json.getString("range")
        val spellDuration = json.getString("duration")
        val spellDesc = json.getJSONArray("desc")
        var desc = ""
        for (i in 0 until spellDesc.length()) {
            desc += spellDesc.getString(i)
        }

        var higherLevel = ""
        if (json.has("higher_level")) {
            val spellHigherLevel = json.getJSONArray("higher_level")
            for (i in 0 until spellHigherLevel.length()) {
                higherLevel += spellHigherLevel.getString(i)
            }
        }

        var spellDamage: JSONObject? = null
        var spellDamageDamageType = ""
        if (json.has("damage")) {
            spellDamage = json.getJSONObject("damage")
            if (spellDamage.has("damage_type")) {
                spellDamageDamageType = spellDamage.getJSONObject("damage_type").getString("name")
            }
        }

        saveSpell(Spell(
            spellName,
            spellLevel,
            spellCastingTime,
            spellRange,
            spellDuration,
            desc,
            higherLevel,
            spellDamageDamageType
        ))

        val spellClasses = json.getJSONArray("classes")
        for (i in 0 until spellClasses.length()) {
            saveSpellClass(SpellClass(spellClasses.getJSONObject(i).getString("name"), spellName))
        }

        if (spellDamage == null) return
        fetchSpellDamage(spellName, spellLevel, spellDamage, saveSpellDamage)
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