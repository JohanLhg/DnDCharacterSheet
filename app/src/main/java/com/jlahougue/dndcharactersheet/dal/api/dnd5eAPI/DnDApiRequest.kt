package com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI

import com.jlahougue.dndcharactersheet.dal.api.ApiRequest
import java.util.Locale

class DnDApiRequest: ApiRequest() {

    companion object {
        private var INSTANCE: DnDApiRequest? = null

        private const val DND_API_URL = "https://www.dnd5eapi.co"

        const val DND_API_SPELLS_URL = "$DND_API_URL/api/spells"
        const val DND_API_DAMAGE_TYPES_URL = "$DND_API_URL/api/damage-types"
        const val DND_API_WEAPON_PROPERTIES_URL = "$DND_API_URL/api/weapon-properties"
        const val DND_API_WEAPON_URL = "$DND_API_URL/api/equipment-categories/weapon"

        fun getInstance(): DnDApiRequest {
            if (INSTANCE == null) {
                INSTANCE = DnDApiRequest()
            }
            return INSTANCE!!
        }

        fun getUrl(url: String) = "$DND_API_URL$url"

        fun getClassLevelsUrl(clazz: String) = "$DND_API_URL/api/classes/${clazz.lowercase(Locale.ROOT)}/levels"
    }
}