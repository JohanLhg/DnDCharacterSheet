package com.jlahougue.dndcharactersheet.dal.dndAPI.dao

import com.jlahougue.dndcharactersheet.dal.dndAPI.DnDAPIRequest
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.STRENGTH
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

        var test = ""
        if (json.has("weapon_range")) {
            val category = json.getString("weapon_range")
            test = when (category) {
                "Melee" -> STRENGTH
                "Ranged" -> DEXTERITY
                else -> ""
            }
        }

        var costStr = ""
        if (json.has("cost")) {
            val cost = json.getJSONObject("cost")
            costStr = cost.getString("quantity") + cost.getString("unit")
        }

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

        var rangeStr = ""
        if (json.has("range")) {
            val range = json.getJSONObject("range")
            val rangeNormal = if (range.has("normal")) range.getInt("normal") else 0
            val rangeLong = if (range.has("long")) range.getInt("long") else 0
            when {
                rangeNormal != 0 && rangeLong != 0 -> rangeStr = "$rangeNormal-$rangeLong m"
                rangeNormal != 0 -> rangeStr = "$rangeNormal m"
                rangeLong != 0 -> rangeStr = "$rangeLong m"
            }
        }

        var throwRangeStr = ""
        if (json.has("throw_range")) {
            val throwRange = json.getJSONObject("throw_range")
            val throwRangeNormal = if (throwRange.has("normal")) throwRange.getInt("normal") else 0
            val throwRangeLong = if (throwRange.has("long")) throwRange.getInt("long") else 0
            when {
                throwRangeNormal != 0 && throwRangeLong != 0 -> throwRangeStr = "$throwRangeNormal-$throwRangeLong m"
                throwRangeNormal != 0 -> throwRangeStr = "$throwRangeNormal m"
                throwRangeLong != 0 -> throwRangeStr = "$throwRangeLong m"
            }
        }

        val weight = if (json.has("weight")) json.getInt("weight") else 0

        var description = ""
        if (json.has("desc")) {
            val desc = json.getJSONArray("desc")
            for (i in 0 until desc.length()) {
                if (i > 0) description += "\n"
                description += desc.getString(i)
            }
        }

        if (json.has("special")) {
            val specialArray = json.getJSONArray("special")
            for (i in 0 until specialArray.length()) {
                if (description.isNotEmpty()) description += "\n"
                description += specialArray.getString(i)
            }
        }

        val weapon = Weapon(
            name = name,
            test = test,
            damage = damageDice,
            damageType = damageType,
            twoHandedDamage = twoHandedDamageDice,
            twoHandedDamageType = twoHandedDamageType,
            range = rangeStr,
            throwRange = throwRangeStr,
            weight = weight,
            cost = costStr,
            description = description
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