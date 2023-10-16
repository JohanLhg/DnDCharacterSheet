package com.jlahougue.dndcharactersheet.dal.open5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.open5eAPI.Open5eAPIRequest.Companion.OPEN5E_API_CLASSES_URL
import org.json.JSONObject
import kotlin.concurrent.thread

class ClassDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchClasses(
        names: List<String>,
        saveClass: (Class) -> Unit,
        fetchLevels: (String) -> Unit,
        setProgress: (Int, Int) -> Unit,
        callback: () -> Unit
    ) {
        val response = apiRequest.sendGet(OPEN5E_API_CLASSES_URL) ?: return
        val json = JSONObject(response)
        setProgress(0, json.getInt("count"))
        val results = json.getJSONArray("results")
        var clazz: JSONObject
        var name: String
        for (i in 0 until results.length()) {
            thread {
                clazz = results.getJSONObject(i)
                name = clazz.getString("name")
                if (!names.contains(name))
                    fetchClass(clazz, saveClass, fetchLevels)
                setProgress(i, json.getInt("count"))
            }
        }
        callback()
    }

    private fun fetchClass(
        clazz: JSONObject,
        saveClass: (Class) -> Unit,
        fetchLevels: (String) -> Unit
    ) {
        val name = clazz.getString("name")
        val hitDie = clazz.getString("hit_dice").removePrefix("1d").toInt()
        val profArmor = clazz.getString("prof_armor")
        val profWeapons = clazz.getString("prof_weapons")
        val profTools = clazz.getString("prof_tools")
        val hasSpellcasting = clazz.has("spellcasting")
        val spellcastingAbility =
            if (hasSpellcasting) clazz.getString("spellcasting_ability")
            else ""

        saveClass(
            Class(
                name,
                hitDie,
                profArmor,
                profWeapons,
                profTools,
                hasSpellcasting,
                spellcastingAbility
            )
        )

        thread { fetchLevels(name) }
    }
}