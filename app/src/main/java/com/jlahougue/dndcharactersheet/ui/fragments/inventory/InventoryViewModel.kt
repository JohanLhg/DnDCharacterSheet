package com.jlahougue.dndcharactersheet.ui.fragments.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.Equipment
import com.jlahougue.dndcharactersheet.dal.entities.Money
import com.jlahougue.dndcharactersheet.dal.entities.Notes
import com.jlahougue.dndcharactersheet.dal.entities.Quests
import com.jlahougue.dndcharactersheet.dal.repositories.EquipmentRepository
import com.jlahougue.dndcharactersheet.dal.repositories.MoneyRepository
import com.jlahougue.dndcharactersheet.dal.repositories.NotesRepository
import com.jlahougue.dndcharactersheet.dal.repositories.QuestsRepository
import kotlin.concurrent.thread

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository = NotesRepository(application)
    private val questsRepository = QuestsRepository(application)
    private val moneyRepository = MoneyRepository(application)
    private val equipmentRepository = EquipmentRepository(application)

    val notes = MutableLiveData<String>(null)
    val quests = MutableLiveData<String>(null)
    val money = MutableLiveData<Money>(null)
    val equipment = MutableLiveData<String>(null)

    var characterID = 0L
        set(value) {
            field = value
            thread {
                notes.postValue(notesRepository.get(value))
                quests.postValue(questsRepository.get(value))
                money.postValue(moneyRepository.get(value))
                equipment.postValue(equipmentRepository.get(value))
            }
        }

    fun updateNotes(notes: String) {
        thread {
            notesRepository.update(Notes(characterID, notes))
        }
    }

    fun updateQuests(quests: String) {
        thread {
            questsRepository.update(Quests(characterID, quests))
        }
    }

    fun updateMoney(copper: Int, silver: Int, electrum: Int, gold: Int, platinum: Int, otherCurrencies: String) {
        thread {
            moneyRepository.update(
                Money(
                    characterID,
                    copper,
                    silver,
                    electrum,
                    gold,
                    platinum,
                    otherCurrencies
                )
            )
        }
    }

    fun updateEquipment(equipment: String) {
        thread {
            equipmentRepository.update(Equipment(characterID, equipment))
        }
    }
}