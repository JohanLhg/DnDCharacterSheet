package com.jlahougue.dndcharactersheet.dal.dndAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import org.json.JSONObject

class DamageTypeDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchDamageTypes(
        names: List<String>,
        saveDamageType: (DamageType) -> Unit,
        setProgress: (Int, Int) -> Unit,
        callback: () -> Unit
    ) {
        val response = apiRequest.sendGet(DnDAPIRequest.DND_API_DAMAGE_TYPES_URL) ?: return
        val json = JSONObject(response)

        val count = json.getInt("count")
        setProgress(0, count)

        val results = json.getJSONArray("results")
        var name: String
        var url: String
        for (i in 0 until results.length()) {
            name = results.getJSONObject(i).getString("name")
            url = results.getJSONObject(i).getString("url")
            if (!names.contains(name)) fetchDamageType(DnDAPIRequest.getUrl(url), saveDamageType)
            setProgress(i, count)
        }
        callback()
    }

    private fun fetchDamageType(
        url: String,
        saveDamageType: (DamageType) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return

        val json = JSONObject(response)
        val name = json.getString("name")
        val desc = json.getString("desc")

        saveDamageType(DamageType(name, desc))
    }
}