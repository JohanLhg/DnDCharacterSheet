package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao

import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.DnDApiRequest
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.DEXTERITY
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository.Companion.STRENGTH
import com.jlahougue.dndcharactersheet.extensions.getIntIfExists
import com.jlahougue.dndcharactersheet.extensions.getJSONArrayIfExists
import com.jlahougue.dndcharactersheet.extensions.getStringIfExists
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class WeaponDao {
    private val apiRequest = DnDApiRequest.getInstance()

    fun fetchWeapons(
        names: List<String>,
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Weapon, List<WeaponProperty>) -> Unit
    ) {
        val response = apiRequest.sendGet(DnDApiRequest.DND_API_WEAPON_URL) ?: return cancel()
        val json = JSONObject(response)

        val weapons = json.getJSONArray("equipment")
        val count = weapons.length()
        if (count == names.size) {
            cancel()
            return
        }

        setProgressMax(count)

        var name: String
        var url: String
        for (i in 0 until weapons.length()) {
            CoroutineScope(Dispatchers.IO).launch {
                name = weapons.getJSONObject(i).getString("name")
                url = weapons.getJSONObject(i).getString("url")
                if (names.contains(name)) skip()
                else fetchWeapon(DnDApiRequest.getUrl(url), cancel, save)
            }
        }
    }

    private fun fetchWeapon(
        url: String,
        cancel: () -> Unit,
        save: (Weapon, List<WeaponProperty>) -> Unit
    ) {
        val response = apiRequest.sendGet(url) ?: return cancel()

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
            val rangeNormal = range.getIntIfExists("normal")
            val rangeLong = range.getIntIfExists("long")
            when {
                rangeNormal != 0 && rangeLong != 0 -> rangeStr = "$rangeNormal-$rangeLong m"
                rangeNormal != 0 -> rangeStr = "$rangeNormal m"
                rangeLong != 0 -> rangeStr = "$rangeLong m"
            }
        }

        var throwRangeStr = ""
        if (json.has("throw_range")) {
            val throwRange = json.getJSONObject("throw_range")
            val throwRangeNormal = throwRange.getIntIfExists("normal")
            val throwRangeLong = throwRange.getIntIfExists("long")
            when {
                throwRangeNormal != 0 && throwRangeLong != 0 -> throwRangeStr = "$throwRangeNormal-$throwRangeLong m"
                throwRangeNormal != 0 -> throwRangeStr = "$throwRangeNormal m"
                throwRangeLong != 0 -> throwRangeStr = "$throwRangeLong m"
            }
        }

        val weight = json.getIntIfExists("weight")

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

        val properties = listOf<WeaponProperty>()
        val weaponProperties = json.getJSONArrayIfExists("properties")
        var property: String
        for (i in 0 until weaponProperties.length()) {
            property = weaponProperties.getJSONObject(i).getString("name")
            properties.plus(WeaponProperty(name, property))
        }

        save(weapon, properties)
    }
}