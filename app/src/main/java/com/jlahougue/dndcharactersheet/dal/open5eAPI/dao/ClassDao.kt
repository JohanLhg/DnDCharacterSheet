package com.jlahougue.dndcharactersheet.dal.open5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.open5eAPI.Open5eAPIRequest.Companion.OPEN5E_API_CLASSES_URL
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import org.json.JSONObject
import kotlin.concurrent.thread

class ClassDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchClasses(
        names: List<String>,
        saveClass: (Class) -> Unit,
        fetchLevels: (String) -> Unit,
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val response = apiRequest.sendGet(OPEN5E_API_CLASSES_URL) ?: return
        val json = JSONObject(response)
        setProgressMax(progressKey, json.getInt("count"))
        val results = json.getJSONArray("results")
        var clazz: JSONObject
        var name: String
        for (i in 0 until results.length()) {
            thread {
                clazz = results.getJSONObject(i)
                name = clazz.getString("name")
                if (!names.contains(name))
                    fetchClass(clazz, saveClass, fetchLevels)
                updateProgress(progressKey)
            }
        }
    }

    private fun fetchClass(
        clazz: JSONObject,
        saveClass: (Class) -> Unit,
        fetchLevels: (String) -> Unit
    ) {
        val name = clazz.getString("name")
        val hitDie = clazz.getString("hit_dice").removePrefix("1d").toInt()
        val equipment = clazz.getString("equipment")
        val profSavingThrows = clazz.getString("prof_saving_throws")
        val profSkills = clazz.getString("prof_skills")
        val profArmor = clazz.getString("prof_armor")
        val profWeapons = clazz.getString("prof_weapons")
        val profTools = clazz.getString("prof_tools")
        val spellcastingAbility = AbilityRepository.getDatabaseCode(clazz.getString("spellcasting_ability"))

        saveClass(
            Class(
                name,
                hitDie,
                equipment,
                profSavingThrows,
                profSkills,
                profArmor,
                profWeapons,
                profTools,
                spellcastingAbility
            )
        )

        thread { fetchLevels(name) }
    }
}