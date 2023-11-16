package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Preferences
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class PreferencesRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).preferencesDao()

    private fun create() = insert(Preferences())

    private fun insert(preferences: Preferences) = roomDao.insert(preferences)

    fun update(preferences: Preferences) = roomDao.update(preferences)

    fun get() = roomDao.get()

    fun getLanguage(callback: (String) -> Unit) {
        val language = roomDao.getLanguage()
        if (language == null) {
            create()
            callback(roomDao.getLanguage()!!)
        } else
            callback(language)
    }

    companion object {
        const val LANGUAGE_EN = "en"
        const val LANGUAGE_FR = "fr"

        const val UNIT_SYSTEM_IMPERIAL = "imperial"
        const val UNIT_SYSTEM_METRIC = "metric"
    }
}