package com.jlahougue.dndcharactersheet.dal.dndAPI

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest.Companion.DND_API_URL
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import org.json.JSONObject

class SpellDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun getSpells(): List<Spell> {
        val spellList = mutableListOf<Spell>()
        val response = apiRequest.sendGet(DND_API_URL + "spells") ?: return spellList

        val json = JSONObject(response)
        val results = json.getJSONArray("results")
        var url = ""
        var spell: Spell?
        for (i in 0 until results.length()) {
            url = results.getJSONObject(i).getString("url")
            spell = getSpell(url)
            if (spell == null) continue
            spellList.add(spell)
        }
        return spellList
    }

    private fun getSpell(url: String): Spell? {
        val response = apiRequest.sendGet(url) ?: return null

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
        val spellHigherLevel = json.getJSONArray("higher_level")
        var higherLevel = ""
        for (i in 0 until spellHigherLevel.length()) {
            higherLevel += spellHigherLevel.getString(i)
        }
        return Spell(
            spellName,
            spellLevel,
            spellCastingTime,
            spellRange,
            spellDuration,
            desc,
            higherLevel
        )
    }
}