package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.dndAPI.dao.PropertyDao
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class PropertyRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).propertyDao()
    private val apiDao = PropertyDao()

    fun fetchAll(setProgress: (Int, Int) -> Unit, callback: () -> Unit) {
        val names = roomDao.getNames()
        apiDao.fetchProperties(names, this::saveProperty, setProgress, callback)
    }

    private fun saveProperty(property: Property) = roomDao.insert(property)
}