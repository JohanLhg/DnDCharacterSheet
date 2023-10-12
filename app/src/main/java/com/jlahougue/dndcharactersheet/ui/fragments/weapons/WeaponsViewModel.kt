package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class WeaponsViewModel(application: Application) : AndroidViewModel(application) {

    var characterID = 0L
        set(value) {
            field = value
        }
}