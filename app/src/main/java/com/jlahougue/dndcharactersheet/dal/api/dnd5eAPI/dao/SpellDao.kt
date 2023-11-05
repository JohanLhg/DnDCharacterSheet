package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest.Companion.SPELLS_URL
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest.Companion.getUrl
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import org.json.JSONObject
import kotlin.concurrent.thread

class SpellDao {
    private val apiRequest = DnD5eApiRequest.getInstance()

    fun fetchSpells(
        names: List<String>,
        saveSpellDamage: (SpellDamage) -> Unit,
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val response = apiRequest.sendGet(SPELLS_URL) ?: return
        val json = JSONObject(response)
        setProgressMax(progressKey, json.getInt("count"))
        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            thread {
                name = results.getJSONObject(i).getString("name")
                url = results.getJSONObject(i).getString("url")
                if (!names.contains(name))
                    fetchSpell(getUrl(url), saveSpellDamage)
                updateProgress(progressKey)
            }
        }
    }

    private fun fetchSpell(
        url: String,
        saveSpellDamage: (SpellDamage) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return

        val json = JSONObject(response)
        val id = json.getString("index")
        val name = json.getString("name")
        val level = json.getInt("level")

        var damage: JSONObject? = null
        var damageDamageType = ""
        if (json.has("damage")) {
            damage = json.getJSONObject("damage")
            if (damage.has("damage_type")) {
                damageDamageType = damage.getJSONObject("damage_type").getString("name")
            }
        }

        if (damage == null) return
        fetchSpellDamage(id, level, damage, saveSpellDamage)
    }

    private fun fetchSpellDamage(id: String, level: Int, jsonDamage: JSONObject, saveSpellDamage: (SpellDamage) -> Unit) {
        //If the spell's damage is based on the slot level
        if (jsonDamage.has("damage_at_slot_level")) {
            val spellDamageDamageAtSlotLevel = jsonDamage.getJSONObject("damage_at_slot_level")
            for (i in level until 9) {
                if (spellDamageDamageAtSlotLevel.has(i.toString())) {
                    saveSpellDamage(SpellDamage(id, i, 0, spellDamageDamageAtSlotLevel.getString(i.toString())))
                }
            }
            return
        }

        //If the spell's damage is based on the character level
        if (jsonDamage.has("damage_at_character_level")) {
            val spellDamageDamageAtCharacterLevel = jsonDamage.getJSONObject("damage_at_character_level")
            for (i in level until 20) {
                if (spellDamageDamageAtCharacterLevel.has(i.toString())) {
                    saveSpellDamage(SpellDamage(id, 0, i, spellDamageDamageAtCharacterLevel.getString(i.toString())))
                }
            }
        }
    }
}