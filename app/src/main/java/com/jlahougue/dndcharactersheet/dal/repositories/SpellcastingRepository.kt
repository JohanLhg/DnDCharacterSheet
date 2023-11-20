package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView
import com.jlahougue.dndcharactersheet.dal.room.DnDDatabase

class SpellcastingRepository(application: Application) {
    val roomDao = DnDDatabase.getInstance(application).spellcastingDao()

    fun get(characterID: Long): LiveData<SpellcastingView> = roomDao.get(characterID)
}
