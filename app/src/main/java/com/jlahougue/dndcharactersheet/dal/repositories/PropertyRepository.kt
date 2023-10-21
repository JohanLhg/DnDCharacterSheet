package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.dndAPI.dao.PropertyDao
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class PropertyRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).propertyDao()
    private val apiDao = PropertyDao()

    fun fetchAll(
        progressKey: Int,
        setProgressMax: (Int, Int) -> Unit,
        updateProgress: (Int) -> Unit
    ) {
        val names = roomDao.getNames()
        apiDao.fetchProperties(
            names,
            this::saveProperty,
            progressKey,
            setProgressMax,
            updateProgress
        )
    }

    private fun saveProperty(property: Property) = roomDao.insert(property)
}