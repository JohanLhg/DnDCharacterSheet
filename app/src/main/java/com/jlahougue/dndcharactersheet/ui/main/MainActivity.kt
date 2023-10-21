package com.jlahougue.dndcharactersheet.ui.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.databinding.ActivityMainBinding
import com.jlahougue.dndcharactersheet.ui.main.MainViewModel.Companion.CHARACTER_ID
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val mainViewModel: MainViewModel by viewModels()

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        if (result == null) return@registerForActivityResult
        mainViewModel.updateProfileImage(result)
        binding.panelProfile.imageProfile.setImageURI(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get the character ID from the intent
        val cid = intent.getLongExtra(CHARACTER_ID, -1L)
        if (cid == -1L) finish()
        mainViewModel.setCharacterId(cid)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        Glide.with(this@MainActivity)
            .load(R.drawable.loading)
            .centerInside()
            .into(binding.panelProfile.imageProfile)

        setupSettingsPanel()

        mainViewModel.characterID.observe(this) {
            binding.viewModel = mainViewModel
            setupNavigation()
            setupProfilePanel()
        }

        binding.buttonProfile.setOnClickListener {
            binding.layoutDrawer.openDrawer(GravityCompat.START)
        }

        binding.buttonSettings.setOnClickListener {
            binding.layoutDrawer.openDrawer(GravityCompat.END)
        }

        onBackPressedDispatcher.addCallback(this) {
            if (binding.layoutDrawer.isDrawerOpen(GravityCompat.END)) {
                binding.layoutDrawer.closeDrawer(GravityCompat.END)
            } else {
                finish()
            }
        }
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        AppBarConfiguration(
            setOf(
                R.id.navigation_stats,
                R.id.navigation_inventory,
                R.id.navigation_spells,
                R.id.navigation_weapons,
                R.id.navigation_subclass
            )
        )
        navView.setupWithNavController(navController)
    }

    private fun setupProfilePanel() {
        binding.panelProfile.apply {
            mainViewModel.loadCharacterImage(this@MainActivity, imageProfile)

            imageProfile.setOnClickListener {
                getImage.launch("image/*")
            }

            buttonLevelUp.setOnClickListener {
                val character = mainViewModel.character.value ?: return@setOnClickListener
                character.level++
                mainViewModel.character.value = character
                mainViewModel.updateCharacter(character)
            }

            editName.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.name = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editRace.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.race = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editClass.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.clazz = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editLevel.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.level = it.toString().toIntOrNull() ?: 0
                mainViewModel.updateCharacter(character)
            }
            editGender.addTextChangedListener{
                val character = mainViewModel.character.value!!
                character.gender = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editAge.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.age = it.toString().toIntOrNull() ?: 0
                mainViewModel.updateCharacter(character)
            }
            editHeight.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.height = it.toString().toDoubleOrNull() ?: 0.0
                mainViewModel.updateCharacter(character)
            }
            editWeight.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.weight = it.toString().toIntOrNull() ?: 0
                mainViewModel.updateCharacter(character)
            }

            editPersonalityTraits.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.personality = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editIdeals.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.ideals = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editBonds.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.bonds = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editFlaws.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.flaws = it.toString()
                mainViewModel.updateCharacter(character)
            }

            editBackgroundTitle.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.backgroundTitle = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editBackground.addTextChangedListener {
                val character = mainViewModel.character.value!!
                character.background = it.toString()
                mainViewModel.updateCharacter(character)
            }
        }
    }

    private fun setupSettingsPanel() {
        binding.panelSettings.apply {
            mainViewModel.getLanguage {
                runOnUiThread {
                    setLanguageDisplay(it)
                }
            }

            imageFR.setOnClickListener { setLocale("fr") }

            imageEN.setOnClickListener { setLocale("en") }

            buttonChangeEmail.setOnClickListener {
                /** TODO: Implement change email
                 * 1. Check if the email is valid
                 * 2. Check if the email confirmation is valid
                 * 3. Check if the email and the email confirmation are the same
                 * 4. Change the email
                 */
            }

            buttonChangePassword.setOnClickListener {
                /** TODO: Implement change password
                 * 1. Check if the password is valid
                 * 2. Check if the password confirmation is valid
                 * 3. Check if the password and the password confirmation are the same
                 * 4. Change the password
                 */
            }

            buttonSignOut.setOnClickListener { mainViewModel.signOut() }

            buttonSaveToRemote.setOnClickListener { mainViewModel.saveToRemote() }

            buttonSwitchCharacter.setOnClickListener { finish() }
        }
    }

    private fun setLanguageDisplay(languageCode: String) {
        binding.panelSettings.apply {
            imageFR.alpha = 0.5f
            imageEN.alpha = 0.5f
            when (languageCode) {
                "fr" -> imageFR.alpha = 1f
                "en" -> imageEN.alpha = 1f
            }
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        mainViewModel.setLanguage(languageCode)
        recreate()
    }
}