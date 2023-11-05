package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest.Companion.getUrl
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.json.JSONObject

class DamageTypeDao {
    private val apiRequest = DnD5eApiRequest.getInstance()

    suspend fun fetchDamageTypes(
        names: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        save: (DamageType) -> Unit
    ) {
        val response = apiRequest.sendGet(DnD5eApiRequest.DAMAGE_TYPES_URL) ?: return cancel()
        val json = JSONObject(response)
        val count = json.getInt("count")
        if (count == names.size) return cancel()

        setProgressMax(count)
        val results = json.getJSONArray("results")

        (0..<results.length()).map {
            CoroutineScope(Dispatchers.IO).launch {
                val name = results.getJSONObject(it).getString("name")
                val url = results.getJSONObject(it).getString("url")
                if (names.contains(name)) skip()
                else fetchDamageType(getUrl(url), skip, updateProgress, save)
            }
        }.joinAll()
    }

    private fun fetchDamageType(
        url: String,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        save: (DamageType) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return skip()

        val json = JSONObject(response)
        val name = json.getString("name")
        val desc = json.getString("desc")

        save(DamageType(name, desc))

        updateProgress()
    }
}