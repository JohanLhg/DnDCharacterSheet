package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.repositories.NotesRepository
import kotlin.concurrent.thread

class SpellsViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository = NotesRepository(application)

    val notes = MutableLiveData<String>(null)

    var characterID = 0L
        set(value) {
            field = value
            thread {
                notes.postValue(notesRepository.get(value))
            }
        }
}