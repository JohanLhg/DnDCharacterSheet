package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import android.util.Log
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnD5eApiRequest
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.STRENGTH
import com.jlahougue.dndcharactersheet.extensions.getIntIfExists
import com.jlahougue.dndcharactersheet.extensions.getJSONArrayIfExists
import com.jlahougue.dndcharactersheet.extensions.getStringIfExists
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.json.JSONObject

class WeaponDao {
    private val apiRequest = DnD5eApiRequest.getInstance()

    suspend fun fetchWeapons(
        names: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        saveWeapon: (Weapon) -> Long,
        saveProperties: (List<WeaponProperty>) -> Unit
    ) {
        val response = apiRequest.sendGet(DnD5eApiRequest.WEAPON_URL) ?: return cancel()
        val json = JSONObject(response)

        val weapons = json.getJSONArray("equipment")
        val count = weapons.length()
        if (count == names.size) return cancel()

        setProgressMax(count)

        (0 until weapons.length()).map {
            CoroutineScope(Dispatchers.IO).launch {
                val name = weapons.getJSONObject(it).getString("name")
                val url = weapons.getJSONObject(it).getString("url")

                if (names.contains(name)) skip()
                else fetchWeapon(
                    DnD5eApiRequest.getUrl(url),
                    skip,
                    updateProgress,
                    saveWeapon,
                    saveProperties
                )
            }
        }.joinAll()
    }

    private fun fetchWeapon(
        url: String,
        skip: () -> Unit,
        updateProgress: () -> Unit,
        saveWeapon: (Weapon) -> Long,
        saveProperties: (List<WeaponProperty>) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return skip()

        val json = JSONObject(response)
        val name = json.getString("name")

        val test = when (json.getStringIfExists("weapon_range")) {
            "Melee" -> STRENGTH
            "Ranged" -> DEXTERITY
            else -> ""
        }

        var costStr = ""
        if (json.has("cost")) {
            val cost = json.getJSONObject("cost")
            costStr = cost.getString("quantity") + cost.getString("unit")
        }

        //region Damage
        var damageDice = ""
        var damageType = ""
        if (json.has("damage")) {
            val damage = json.getJSONObject("damage")
            damageDice = damage.getString("damage_dice")
            damageType = damage.getJSONObject("damage_type").getString("name")
        }

        var twoHandedDamageDice = ""
        var twoHandedDamageType = ""
        if (json.has("two_handed_damage")) {
            val twoHandedDamage = json.getJSONObject("two_handed_damage")
            twoHandedDamageDice = twoHandedDamage.getString("damage_dice")
            twoHandedDamageType = twoHandedDamage.getJSONObject("damage_type").getString("name")
        }
        //endregion

        //region Range
        var rangeMin = 0
        var rangeMax = 0
        if (json.has("range")) {
            val range = json.getJSONObject("range")
            rangeMin = range.getIntIfExists("normal")
            rangeMax = range.getIntIfExists("long")
        }

        var throwRangeMin = 0
        var throwRangeMax = 0
        if (json.has("throw_range")) {
            val throwRange = json.getJSONObject("throw_range")
            throwRangeMin = throwRange.getIntIfExists("normal")
            throwRangeMax = throwRange.getIntIfExists("long")
        }

        if (test == DEXTERITY) {
            if (throwRangeMin == 0) throwRangeMin = rangeMin
            if (throwRangeMax == 0) throwRangeMax = rangeMax
        }
        val normalRange = rangeMin
        //endregion

        val weight = json.getIntIfExists("weight")

        //region Description
        var description = ""
        val desc = json.getJSONArrayIfExists("desc")
        for (i in 0 until desc.length()) {
            if (i > 0) description += "\n"
            description += desc.getString(i)
        }

        val specialArray = json.getJSONArrayIfExists("special")
        for (i in 0 until specialArray.length()) {
            if (description.isNotEmpty()) description += "\n"
            description += specialArray.getString(i)
        }
        //endregion

        val failed = saveWeapon(
            Weapon(
                name,
                test,
                damageDice,
                damageType,
                twoHandedDamageDice,
                twoHandedDamageType,
                normalRange,
                throwRangeMin,
                throwRangeMax,
                weight,
                costStr,
                description
            )
        ) == -1L

        if (failed) return skip()

        val properties = listOf<WeaponProperty>()
        val weaponProperties = json.getJSONArrayIfExists("properties")
        Log.d("WeaponDao", weaponProperties.toString())
        var property: String
        for (i in 0 until weaponProperties.length()) {
            property = weaponProperties.getJSONObject(i).getString("name")
            properties.plus(WeaponProperty(name, property))
        }
        saveProperties(properties)

        updateProgress()
    }
}