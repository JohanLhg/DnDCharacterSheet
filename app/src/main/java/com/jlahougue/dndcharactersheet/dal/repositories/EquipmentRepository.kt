package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Equipment
import com.jlahougue.dndcharactersheet.dal.firebase.dao.EquipmentDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class EquipmentRepository(application: Application) {
    private val roomDao = DnDDatabase.getInstance(application).equipmentDao()
    private val firebaseDao = EquipmentDao()

    fun create(characterID: Long) {
        insert(Equipment(characterID))
    }

    fun insert(equipment: Equipment) {
        roomDao.insert(equipment)
        firebaseDao.save(equipment)
    }

    fun saveToLocal(equipment: Equipment) = roomDao.insert(equipment)

    fun update(equipment: Equipment) {
        roomDao.update(equipment)
        firebaseDao.save(equipment)
    }

    fun get(characterID: Long) = roomDao.get(characterID)
}