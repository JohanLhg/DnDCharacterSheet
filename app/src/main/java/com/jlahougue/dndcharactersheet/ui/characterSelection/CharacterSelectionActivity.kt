package com.jlahougue.dndcharactersheet.ui.characterSelection

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.entities.Character.Companion.CHARACTER_ID
import com.jlahougue.dndcharactersheet.databinding.ActivityCharacterSelectionBinding
import com.jlahougue.dndcharactersheet.ui.authentication.AuthActivity
import com.jlahougue.dndcharactersheet.ui.characterSelection.CharacterSelectionViewModel.Companion.AUTO_LOAD
import com.jlahougue.dndcharactersheet.ui.main.MainActivity


class CharacterSelectionActivity : AppCompatActivity(), CharacterAdapter.CharacterListener {

    private lateinit var binding: ActivityCharacterSelectionBinding

    private val characterSelectionViewModel: CharacterSelectionViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.application)
            .create(CharacterSelectionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra(AUTO_LOAD, false)) {
            characterSelectionViewModel.getFavoriteCharacter {
                if (it != null) openCharacter(it)
            }
        }

        binding.recyclerCharacters.adapter = CharacterAdapter(this, this)

        binding.buttonSignOut.setOnClickListener { signOut() }

        characterSelectionViewModel.characters.observe(this) {
            (binding.recyclerCharacters.adapter as CharacterAdapter).characters = it
        }
    }

    private fun signOut() {
        characterSelectionViewModel.signOut()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openCharacter(characterID: Long) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(CHARACTER_ID, characterID)
        }
        startActivity(intent)
    }

    override fun onCharacterClick(characterID: Long) = openCharacter(characterID)

    override fun onCharacterCreate() {
        characterSelectionViewModel.createCharacter { characterID ->
            openCharacter(characterID)
        }
    }

    override fun onCharacterDelete(character: Character) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_delete_character, character.name))
            .setMessage(getString(R.string.text_delete_character, character.name))
            .setIcon(R.drawable.trash)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ -> characterSelectionViewModel.deleteCharacter(character.id) }
            .setNegativeButton(android.R.string.cancel, null).show()
    }

    override fun onCharacterMadeFavorite(characterID: Long, isFavorite: Boolean) {
        characterSelectionViewModel.updateFavoriteCharacter(characterID, isFavorite)
    }

    override fun loadCharacterImage(view: ImageView, characterID: Long) {
        characterSelectionViewModel.loadCharacterImage(characterID, this, view)
    }
}