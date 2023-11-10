package com.jlahougue.dndcharactersheet.ui.main

import android.os.Bundle
import android.view.View
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
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.LANGUAGE_EN
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.LANGUAGE_FR
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.UNIT_SYSTEM_IMPERIAL
import com.jlahougue.dndcharactersheet.dal.repositories.PreferencesRepository.Companion.UNIT_SYSTEM_METRIC
import com.jlahougue.dndcharactersheet.databinding.ActivityMainBinding
import com.jlahougue.dndcharactersheet.extensions.observeNonNull
import com.jlahougue.dndcharactersheet.ui.detailsDialogs.DialogClassDetails
import com.jlahougue.dndcharactersheet.ui.fragments.spells.spellDetails.SpellDetailsDialog
import com.jlahougue.dndcharactersheet.ui.main.MainViewModel.Companion.CHARACTER_ID
import com.jlahougue.dndcharactersheet.ui.main.profile.ClassAdapter
import com.jlahougue.dndcharactersheet.ui.main.profile.DialogClassSelection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity(),
    DialogClassSelection.DialogClassSelectionListener,
    ClassAdapter.ClassListener {

    private lateinit var binding: ActivityMainBinding

    val mainViewModel: MainViewModel by viewModels()

    private val bottomNavigationView: BottomNavigationView by lazy {
        binding.navView
    }

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

        mainViewModel.preferences.observeNonNull(this) {
            setLanguageDisplay(it.language)
        }

        mainViewModel.characterID.observeNonNull(this) {
            binding.viewModel = mainViewModel
            setupNavigation()
            setupProfilePanel()
        }

        mainViewModel.character.observeNonNull(this) {
            bottomNavigationView.menu.findItem(R.id.navigation_spells).isVisible = it.isSpellcaster()
        }

        binding.buttonProfile.setOnClickListener {
            binding.layoutDrawer.openDrawer(GravityCompat.START)
        }

        binding.buttonSettings.setOnClickListener {
            binding.layoutDrawer.openDrawer(GravityCompat.END)
        }

        onBackPressedDispatcher.addCallback(this) {
            binding.layoutDrawer.apply {
                when {
                    isDrawerOpen(GravityCompat.START) -> closeDrawer(GravityCompat.START)
                    isDrawerOpen(GravityCompat.END) -> closeDrawer(GravityCompat.END)
                    else -> finish()
                }
            }
        }
    }

    private fun setupNavigation() {
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
        bottomNavigationView.setupWithNavController(navController)
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
                if (!editName.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.name = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editRace.addTextChangedListener {
                if (!editRace.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.race = it.toString()
                mainViewModel.updateCharacter(character)
            }
            buttonSelectClass.setOnClickListener {
                mainViewModel.getClasses { classes ->
                    CoroutineScope(Dispatchers.Main).launch {
                        DialogClassSelection(
                            classes,
                            this@MainActivity,
                            this@MainActivity
                        ).show(
                            supportFragmentManager,
                            DialogClassSelection.TAG
                        )
                    }
                }
            }
            editLevel.addTextChangedListener {
                if (!editLevel.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.level = it.toString().toIntOrNull() ?: 0
                if (character.level == 0) {
                    editLevel.clearFocus()
                    editLevel.requestFocus()
                }
                mainViewModel.updateCharacter(character)
            }
            editGender.addTextChangedListener{
                if (!editGender.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.gender = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editAge.addTextChangedListener {
                if (!editAge.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.age = it.toString().toIntOrNull() ?: 0
                if (character.age == 0) {
                    editAge.clearFocus()
                    editAge.requestFocus()
                }
                mainViewModel.updateCharacter(character)
            }
            editHeight.addTextChangedListener {
                if (!editHeight.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.height = it.toString().toDoubleOrNull() ?: 0.0
                if (character.height == 0.0) {
                    editHeight.clearFocus()
                    editHeight.requestFocus()
                }
                //mainViewModel.updateCharacter(character)
            }
            editHeight.onFocusChangeListener = View.OnFocusChangeListener { view, isFocused ->
                if (!isFocused) {
                    val character = mainViewModel.character.value!!
                    character.height = editHeight.text.toString().toDoubleOrNull() ?: 0.0
                    mainViewModel.updateCharacter(character)
                }
            }
            editWeight.addTextChangedListener {
                if (!editWeight.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.weight = it.toString().toIntOrNull() ?: 0
                if (character.weight == 0) {
                    editWeight.clearFocus()
                    editWeight.requestFocus()
                }
                mainViewModel.updateCharacter(character)
            }

            editPersonalityTraits.addTextChangedListener {
                if (!editPersonalityTraits.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.personality = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editIdeals.addTextChangedListener {
                if (!editIdeals.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.ideals = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editBonds.addTextChangedListener {
                if (!editBonds.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.bonds = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editFlaws.addTextChangedListener {
                if (!editFlaws.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.flaws = it.toString()
                mainViewModel.updateCharacter(character)
            }

            editBackgroundTitle.addTextChangedListener {
                if (!editBackgroundTitle.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.backgroundTitle = it.toString()
                mainViewModel.updateCharacter(character)
            }
            editBackground.addTextChangedListener {
                if (!editBackground.hasFocus()) return@addTextChangedListener
                val character = mainViewModel.character.value!!
                character.background = it.toString()
                mainViewModel.updateCharacter(character)
            }
        }
    }

    private fun setupSettingsPanel() {
        binding.panelSettings.apply {
            imageFR.setOnClickListener { setLocale(LANGUAGE_FR) }

            imageEN.setOnClickListener { setLocale(LANGUAGE_EN) }

            buttonImperial.setOnClickListener {
                mainViewModel.updatePreferences(unitSystem = UNIT_SYSTEM_IMPERIAL)
            }

            buttonMetric.setOnClickListener {
                mainViewModel.updatePreferences(unitSystem = UNIT_SYSTEM_METRIC)
            }

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

            buttonSwitchCharacter.setOnClickListener { finish() }
        }
    }

    private fun setLanguageDisplay(languageCode: String) {
        binding.panelSettings.apply {
            imageFR.alpha = 0.5f
            imageEN.alpha = 0.5f
            when (languageCode) {
                LANGUAGE_FR -> imageFR.alpha = 1f
                LANGUAGE_EN -> imageEN.alpha = 1f
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
        mainViewModel.updatePreferences(language = languageCode)
        recreate()
    }

    override fun onClassClicked(clazz: Class) {
        DialogClassDetails(clazz).show(supportFragmentManager, SpellDetailsDialog.TAG)
    }

    override fun setClass(clazz: Class) {
        val character = mainViewModel.character.value!!
        character.setClass(clazz)
        mainViewModel.updateCharacter(character)
        binding.panelProfile.editClass.text = clazz.name
    }
}