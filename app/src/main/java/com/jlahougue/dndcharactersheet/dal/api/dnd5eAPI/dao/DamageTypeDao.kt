package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnDApiRequest
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnDApiRequest.Companion.getUrl
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import org.json.JSONObject
import kotlin.concurrent.thread

class DamageTypeDao {
    private val apiRequest = DnDApiRequest.getInstance()

    fun fetchDamageTypes(
        names: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (DamageType) -> Unit
    ) {
        val response = apiRequest.sendGet(DnDApiRequest.DND_API_DAMAGE_TYPES_URL) ?: return cancel()
        val json = JSONObject(response)
        val count = json.getInt("count")
        if (count == names.size) return cancel()

        setProgressMax(count)
        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            thread {
                name = results.getJSONObject(i).getString("name")
                url = results.getJSONObject(i).getString("url")
                if (names.contains(name)) skip()
                else fetchDamageType(getUrl(url), skip, save)
            }
        }
    }

    private fun fetchDamageType(
        url: String,
        skip: () -> Unit,
        save: (DamageType) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return skip()

        val json = JSONObject(response)
        val name = json.getString("name")
        val desc = json.getString("desc")

        save(DamageType(name, desc))
    }
}