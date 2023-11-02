package com.jlahougue.dndcharactersheet.dal.api.open5eAPI

import com.jlahougue.dndcharactersheet.dal.api.ApiRequest

class Open5eApiRequest: ApiRequest() {

    companion object {
        private var INSTANCE: Open5eApiRequest? = null

        private const val OPEN5E_API_URL = "https://api.open5e.com/v1"

        const val CLASSES_URL = "$OPEN5E_API_URL/classes"
        const val SPELLS_CHECK_URL = "$OPEN5E_API_URL/spells/?limit=1"
        const val SPELLS_URL = "$OPEN5E_API_URL/spells/?limit=999999"

        fun getInstance(): Open5eApiRequest {
            if (INSTANCE == null) {
                INSTANCE = Open5eApiRequest()
            }
            return INSTANCE!!
        }
    }
}