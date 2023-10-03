package com.jlahougue.dndcharactersheet.ui.fragments.profile

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterRepository
import kotlin.concurrent.thread

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository = CharacterRepository(application)

    val character = MutableLiveData<Character>(null)

    var characterID = 0L
        set(value) {
            field = value
            thread { character.postValue(characterRepository.get(value)) }
        }

    fun updateCharacter(character: Character) {
        thread { characterRepository.update(character) }
    }

    fun loadCharacterImage(context: Context, imageProfile: ImageView) {
        thread { characterRepository.loadImage(characterID, context, imageProfile) }
    }

    fun updateProfileImage(uri: Uri) {
        thread { characterRepository.uploadImage(characterID, uri) }
    }
}