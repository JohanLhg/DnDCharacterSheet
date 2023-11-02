package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnDApiRequest
import com.jlahougue.dndcharactersheet.dal.entities.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class PropertyDao {
    private val apiRequest = DnDApiRequest.getInstance()

    fun fetchProperties(
        names: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Property) -> Unit
    ) {
        val response = apiRequest.sendGet(DnDApiRequest.DND_API_WEAPON_PROPERTIES_URL) ?: return cancel()
        val json = JSONObject(response)
        val count = json.getInt("count")
        if (count == names.size) return cancel()

        setProgressMax(count)
        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            CoroutineScope(Dispatchers.IO).launch {
                name = results.getJSONObject(i).getString("name")
                url = results.getJSONObject(i).getString("url")
                if (names.contains(name)) skip()
                else fetchProperty(DnDApiRequest.getUrl(url), skip, save)
            }
        }
    }

    private fun fetchProperty(
        url: String,
        skip: () -> Unit,
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

        save(Property(name = name, description = desc))
    }
}