package com.jlahougue.dndcharactersheet.dal.dndAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import org.json.JSONObject

class WeaponDao {
    private val apiRequest = DnDAPIRequest.getInstance()

    fun fetchWeapons(
        names: List<String>,
        saveWeapon: (Weapon) -> Long,
        saveProperty: (WeaponProperty) -> Unit,
        setProgress: (Int, Int) -> Unit,
        callback: () -> Unit
    ) {
        val response = apiRequest.sendGet(DnDAPIRequest.DND_API_WEAPON_URL) ?: return
        val json = JSONObject(response)

        val weapons = json.getJSONArray("equipment")
        val count = weapons.length()
        setProgress(0, count)

        var name: String
        var url: String
        for (i in 0 until weapons.length()) {
            name = weapons.getJSONObject(i).getString("name")
            url = weapons.getJSONObject(i).getString("url")
            if (!names.contains(name)) fetchWeapon(DnDAPIRequest.getUrl(url), saveWeapon, saveProperty)
            setProgress(i, count)
        }
        callback()
    }

    private fun fetchWeapon(
        url: String,
        saveWeapon: (Weapon) -> Long,
        saveProperty: (WeaponProperty) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return

        val json = JSONObject(response)
        val name = json.getString("name")
        val cost = if (json.has("cost")) json.getString("cost") else ""

        var damageDice = ""
        var damageType = ""
        if (json.has("damage")) {
            val damage = json.getJSONObject("damage")
            damageDice = damage.getString("damage_dice")
            damageType = damage.getJSONObject("damage_type").getString("name")
        }

        var rangeNormal = 0
        var rangeLong = 0
        if (json.has("range")) {
            val range = json.getJSONObject("range")
            rangeNormal = if (range.has("normal")) range.getInt("normal") else 0
            rangeLong = if (range.has("long")) range.getInt("long") else 0
        }

        val weight = if (json.has("weight")) json.getDouble("weight") else 0.0

        var description = ""
        if (json.has("desc")) {
            val desc = json.getJSONArray("desc")
            for (i in 0 until desc.length()) {
                if (i > 0) description += "\n"
                description += desc.getString(i)
            }
        }

        var special = ""
        if (json.has("special")) {
            val specialArray = json.getJSONArray("special")
            for (i in 0 until specialArray.length()) {
                if (i > 0) special += "\n"
                special += specialArray.getString(i)
            }
        }

        val weapon = Weapon(
            name = name,
            damage = damageDice,
            damageType = damageType,
            rangeNormal = rangeNormal,
            rangeLong = rangeLong,
            weight = weight,
            cost = cost,
            description = description,
            special = special
        )
        val id = saveWeapon(weapon)

        if (!json.has("properties")) return
        val properties = json.getJSONArray("properties")
        var property: String
        for (i in 0 until properties.length()) {
            property = properties.getJSONObject(i).getString("name")
            saveProperty(WeaponProperty(id, property))
        }
    }
}