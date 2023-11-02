package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.api.dnd5eAPI.dao.PropertyDao
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class PropertyRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).propertyDao()
    private val apiDao = PropertyDao()

    fun fetchAll(
        cancel: () -> Unit,
        setProgressMax: (Int) -> Unit,
        skip: () -> Unit,
        save: (Property) -> Unit
    ) {
        val names = roomDao.getNames()
        apiDao.fetchProperties(
            names,
            cancel,
            setProgressMax,
            skip,
            save
        )
    }

    fun save(property: Property) = roomDao.insert(property)
}