package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI

import com.jlahougue.dndcharactersheet.dal.api.ApiRequest
import java.util.Locale

class DnD5eApiRequest: ApiRequest() {

    companion object {
        private var INSTANCE: DnD5eApiRequest? = null

        private const val DND_API_URL = "https://www.dnd5eapi.co"

        const val SPELLS_URL = "$DND_API_URL/api/spells"
        const val DAMAGE_TYPES_URL = "$DND_API_URL/api/damage-types"
        const val WEAPON_PROPERTIES_URL = "$DND_API_URL/api/weapon-properties"
        const val WEAPON_URL = "$DND_API_URL/api/equipment-categories/weapon"

        fun getInstance(): DnD5eApiRequest {
            if (INSTANCE == null) {
                INSTANCE = DnD5eApiRequest()
            }
            return INSTANCE!!
        }

        fun getUrl(url: String) = "$DND_API_URL$url"

        fun getClassLevelsUrl(clazz: String) = "$DND_API_URL/api/classes/${clazz.lowercase(Locale.ROOT)}/levels"
    }
}