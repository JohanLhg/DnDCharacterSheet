package com.jlahougue.dndcharactersheet.dal.api.open5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest
import com.jlahougue.dndcharactersheet.dal.api.open5eAPI.Open5eApiRequest.Companion.CLASSES_URL
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class ClassDao {
    private val apiRequest = Open5eApiRequest.getInstance()

    fun fetchClasses(
        names: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Class) -> Unit
    ) {
        val response = apiRequest.sendGet(CLASSES_URL) ?: return cancel()
        val json = JSONObject(response)
        val count = json.getInt("count")
        if (count == names.size) return cancel()

        setProgressMax(count)
        val results = json.getJSONArray("results")
        var clazz: JSONObject
        var name: String
        for (i in 0 until results.length()) {
            CoroutineScope(Dispatchers.IO).launch {
                clazz = results.getJSONObject(i)
                name = clazz.getString("name")
                if (names.contains(name)) skip()
                else fetchClass(clazz, save)
            }
        }
    }

    private fun fetchClass(
        clazz: JSONObject,
        save: (Class) -> Unit
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

        save(
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
    }
}