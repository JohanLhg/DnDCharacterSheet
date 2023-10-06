package com.jlahougue.dndcharactersheet.dal.dndAPI

import okhttp3.OkHttpClient
import okhttp3.Request

class DnDAPIRequest {

    companion object {
        private var INSTANCE: DnDAPIRequest? = null

        val DND_API_URL = "https://www.dnd5eapi.co/api/"

        fun getInstance(): DnDAPIRequest {
            if (INSTANCE == null) {
                INSTANCE = DnDAPIRequest()
            }
            return INSTANCE!!
        }
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