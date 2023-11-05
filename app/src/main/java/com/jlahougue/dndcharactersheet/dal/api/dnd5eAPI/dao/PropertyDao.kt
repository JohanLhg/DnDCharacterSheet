package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest
import com.jlahougue.dndcharactersheet.dal.entities.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.json.JSONObject

class PropertyDao {
    private val apiRequest = DnD5eApiRequest.getInstance()

    suspend fun fetchProperties(
        names: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        save: (Property) -> Unit
    ) {
        val response = apiRequest.sendGet(DnD5eApiRequest.WEAPON_PROPERTIES_URL) ?: return cancel()
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
                else fetchProperty(DnD5eApiRequest.getUrl(url), skip, updateProgress, save)
            }
        }.joinAll()
    }

    private fun fetchProperty(
        url: String,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        save: (Property) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return skip()

        val json = JSONObject(response)
        val name = json.getString("name")
        val descArray = json.getJSONArray("desc")
        var desc = ""
        for (i in 0 until descArray.length()) {
            if (i != 0) desc += "\n\n"
            desc += descArray.getString(i)
        }

        save(Property(name, desc))

        updateProgress()
    }
}