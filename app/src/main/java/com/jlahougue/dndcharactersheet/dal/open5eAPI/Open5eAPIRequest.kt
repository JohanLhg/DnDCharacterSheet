package com.jlahougue.dndcharactersheet.dal.open5eAPI

import okhttp3.OkHttpClient
import okhttp3.Request

class Open5eAPIRequest {

    companion object {
        private var INSTANCE: Open5eAPIRequest? = null

        private const val OPEN5E_API_URL = "https://api.open5e.com/v1"

        const val OPEN5E_API_CLASSES_URL = "$OPEN5E_API_URL/classes"
        const val OPEN5E_API_SPELLS_URL = "$OPEN5E_API_URL/spells"
        const val OPEN5E_API_DAMAGE_TYPES_URL = "$OPEN5E_API_URL/api/damage-types"
        const val OPEN5E_API_WEAPON_PROPERTIES_URL = "$OPEN5E_API_URL/api/weapon-properties"
        const val OPEN5E_API_WEAPON_URL = "$OPEN5E_API_URL/api/equipment-categories/weapon"

        fun getInstance(): Open5eAPIRequest {
            if (INSTANCE == null) {
                INSTANCE = Open5eAPIRequest()
            }
            return INSTANCE!!
        }

        fun getUrl(url: String) = "$OPEN5E_API_URL$url"
    }

    private val client = OkHttpClient()

    @Throws(Exception::class)
    fun sendGet(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return if (response.isSuccessful) {
                    response.body()?.string()
                } else {
                    throw Exception(
                        response.message().ifEmpty { "Error : " + response.code() }
                    )
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}