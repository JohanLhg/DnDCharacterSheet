package com.jlahougue.dndcharactersheet.dal.dndAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.entities.Property
import org.json.JSONObject
import kotlin.concurrent.thread

class PropertyDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchProperties(
        names: List<String>,
        saveProperty: (Property) -> Unit,
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val response = apiRequest.sendGet(DnDAPIRequest.DND_API_WEAPON_PROPERTIES_URL) ?: return
        val json = JSONObject(response)

        val count = json.getInt("count")
        setProgressMax(progressKey, count)

        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            thread {
                name = results.getJSONObject(i).getString("name")
                url = results.getJSONObject(i).getString("url")
                if (!names.contains(name)) fetchProperty(DnDAPIRequest.getUrl(url), saveProperty)
                updateProgress(progressKey)
            }
        }
    }

    private fun fetchProperty(
        url: String,
        saveProperty: (Property) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return

        val json = JSONObject(response)
        val name = json.getString("name")
        val descArray = json.getJSONArray("desc")
        var desc = ""
        for (i in 0 until descArray.length()) {
            if (i != 0) desc += "\n\n"
            desc += descArray.getString(i)
        }

        saveProperty(Property(name = name, description = desc))
    }
}