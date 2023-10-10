package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.jlahougue.dndcharactersheet.dal.entities.Spellcasting
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView
import com.jlahougue.dndcharactersheet.dal.firebase.dao.SpellcastingDao
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellcastingRepository(application: Application) {
    val roomDao = DnDDatabase.getInstance(application).spellcastingDao()
    val firebaseDao = SpellcastingDao()

    fun create(characterID: Long) {
        val spellcasting = Spellcasting(characterID)
        roomDao.insert(spellcasting)
        firebaseDao.save(spellcasting)
    }

    fun saveToLocal(characterID: Long, spellcastingAbility: String) {
        roomDao.insert(Spellcasting(characterID, spellcastingAbility))
    }

    fun getAbility(characterID: Long) = roomDao.getAbility(characterID)

    fun get(characterID: Long): LiveData<SpellcastingView> = roomDao.get(characterID)
}
