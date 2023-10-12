package com.jlahougue.dndcharactersheet.ui.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.databinding.ActivityMainBinding
import com.jlahougue.dndcharactersheet.ui.main.MainViewModel.Companion.CHARACTER_ID
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val mainViewModel: MainViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.application)
            .create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get the character ID from the intent
        val cid = intent.getLongExtra(CHARACTER_ID, -1L)
        if (cid == -1L) finish()
        mainViewModel.characterID.postValue(cid)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration(
            setOf(
                R.id.navigation_profile,
                R.id.navigation_stats,
                R.id.navigation_inventory,
                R.id.navigation_spells,
                R.id.navigation_weapons
            )
        )
        navView.setupWithNavController(navController)

        setupSettingsPanel()

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