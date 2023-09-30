package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Preferences
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class PreferencesRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).preferencesDao()

    fun create() = insert(Preferences())

    fun insert(preferences: Preferences) = roomDao.insert(preferences)

    fun update(preferences: Preferences) = roomDao.update(preferences)
}