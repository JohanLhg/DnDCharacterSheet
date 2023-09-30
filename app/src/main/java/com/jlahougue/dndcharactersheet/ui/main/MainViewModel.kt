package com.jlahougue.dndcharactersheet.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val preferencesRepository = PreferencesRepository(application)
    //private val authRepository = AuthRepository()
}