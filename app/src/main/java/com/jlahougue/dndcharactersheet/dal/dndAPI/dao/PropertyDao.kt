package com.jlahougue.dndcharactersheet.dal.dndAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.entities.Property
import org.json.JSONObject

class PropertyDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchProperties(
        names: List<String>,
        saveProperty: (Property) -> Unit,
        setProgress: (Int, Int) -> Unit,
        callback: () -> Unit
    ) {
        val response = apiRequest.sendGet(DnDAPIRequest.DND_API_WEAPON_PROPERTIES_URL) ?: return
        val json = JSONObject(response)

        val count = json.getInt("count")
        setProgress(0, count)

        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            name = results.getJSONObject(i).getString("name")
            url = results.getJSONObject(i).getString("url")
            if (!names.contains(name)) fetchProperty(DnDAPIRequest.getUrl(url), saveProperty)
            setProgress(i, count)
        }
        callback()
    }

    private fun fetchProperty(
        url: String,
        saveProperty: (Property) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return

        val json = JSONObject(response)
        val name = json.getString("name")
        val desc = json.getString("desc")

        saveProperty(Property(name = name, description = desc))
    }
}