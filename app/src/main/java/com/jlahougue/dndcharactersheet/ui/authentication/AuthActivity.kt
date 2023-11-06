package com.jlahougue.dndcharactersheet.ui.authentication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.databinding.ActivityAuthBinding
import com.jlahougue.dndcharactersheet.domainLayer.apiFetch.FetchAllFromApiUseCase.Companion.CLASSES
import com.jlahougue.dndcharactersheet.domainLayer.apiFetch.FetchAllFromApiUseCase.Companion.DAMAGE_TYPES
import com.jlahougue.dndcharactersheet.domainLayer.apiFetch.FetchAllFromApiUseCase.Companion.PROPERTIES
import com.jlahougue.dndcharactersheet.domainLayer.apiFetch.FetchAllFromApiUseCase.Companion.SPELLS
import com.jlahougue.dndcharactersheet.domainLayer.apiFetch.FetchAllFromApiUseCase.Companion.WEAPONS
import com.jlahougue.dndcharactersheet.ui.authentication.AuthViewModel.Companion.LANGUAGE_LOADED
import com.jlahougue.dndcharactersheet.ui.authentication.AuthViewModel.Companion.LOGIN
import com.jlahougue.dndcharactersheet.ui.authentication.AuthViewModel.Companion.REGISTER
import com.jlahougue.dndcharactersheet.ui.authentication.AuthViewModel.Companion.SEARCHING_FOR_CHARACTER
import com.jlahougue.dndcharactersheet.ui.characterSelection.CharacterSelectionActivity
import com.jlahougue.dndcharactersheet.ui.characterSelection.CharacterSelectionViewModel.Companion.AUTO_LOAD
import java.util.Locale

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.application)
            .create(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.getBooleanExtra(LANGUAGE_LOADED, false)) {
            authViewModel.getLanguage {
                runOnUiThread {
                    if (!setLocale(it))
                        setupActivity()
                }
            }
            return
        }

        setupActivity()
    }

    private fun setupActivity() {
        if (authViewModel.isLoggedIn()) {
            startLoading()
        }

        authViewModel.currentProgressMax.observe(this) { max ->
            binding.progressBar.max = max
        }

        authViewModel.currentProgress.observe(this) { progress ->
            binding.progressBar.progress = progress
        }

        authViewModel.authMode.observe(this) {
            if (it == LOGIN) {
                binding.labelPasswordConfirm.visibility = GONE
                binding.editPasswordConfirm.visibility = GONE

                binding.buttonAuthenticate.setOnClickListener { login() }
                binding.buttonAuthenticate.setText(R.string.login)

                binding.textSwitchAuth.setText(R.string.signup_text)
                binding.textSwitchAuth.setOnClickListener {
                    authViewModel.authMode.value = REGISTER
                }
            } else {
                binding.labelPasswordConfirm.visibility = VISIBLE
                binding.editPasswordConfirm.visibility = VISIBLE

                binding.buttonAuthenticate.setOnClickListener { signUp() }
                binding.buttonAuthenticate.setText(R.string.signup)

                binding.textSwitchAuth.setText(R.string.login_text)
                binding.textSwitchAuth.setOnClickListener {
                    authViewModel.authMode.value = LOGIN
                }
            }
        }
    }

    private fun startLoading() {
        binding.textLoading.visibility = VISIBLE
        binding.progressBar.visibility = INVISIBLE
        binding.layoutAuth.visibility = GONE

        authViewModel.load(this)

        authViewModel.currentIdentifier.observe(this) {
            when (it) {
                SEARCHING_FOR_CHARACTER -> binding.textLoading.text = resources.getString(R.string.searching_for_character)
                CLASSES -> {
                    binding.textLoading.text = resources.getString(R.string.fetching_classes)
                    binding.progressBar.visibility = VISIBLE
                }
                SPELLS -> {
                    binding.textLoading.text = resources.getString(R.string.fetching_spells)
                    binding.progressBar.visibility = VISIBLE
                }
                DAMAGE_TYPES -> {
                    binding.textLoading.text = resources.getString(R.string.fetching_damage_types)
                    binding.progressBar.visibility = VISIBLE
                }
                PROPERTIES -> {
                    binding.textLoading.text = resources.getString(R.string.fetching_weapon_properties)
                    binding.progressBar.visibility = VISIBLE
                }
                WEAPONS -> {
                    binding.textLoading.text = resources.getString(R.string.fetching_weapons)
                    binding.progressBar.visibility = VISIBLE
                }
            }
        }

        authViewModel.currentProgressMax.observe(this) {
            binding.progressBar.max = it
        }

        authViewModel.currentProgress.observe(this) {
            binding.progressBar.progress = it
        }

        authViewModel.finished.observe(this) {
            if (it) {
                val intent = Intent(this, CharacterSelectionActivity::class.java).apply {
                    putExtra(AUTO_LOAD, true)
                }
                startActivity(intent)
            }
        }
    }

    private fun login() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (email.isBlank()) {
            Toast.makeText(
                this,
                R.string.warning_email_empty,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(
                this,
                R.string.warning_password_empty,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val progressDialog = ProgressDialog.show(
            this,
            getText(R.string.logging_in),
            getText(R.string.logging_in_text)
        )

        authViewModel.login(email, password) { success ->
            progressDialog.dismiss()
            if (success) {
                startLoading()
                Toast.makeText(
                    this,
                    R.string.login_success,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    R.string.login_failed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun signUp() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        val passwordConfirm = binding.editPasswordConfirm.text.toString()

        if (email == "") {
            Toast.makeText(
                this,
                R.string.warning_email_empty,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(
                this,
                R.string.warning_password_invalid,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password != passwordConfirm) {
            Toast.makeText(
                this,
                R.string.warning_password_mismatch,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val progressDialog = ProgressDialog.show(
            this,
            getString(R.string.signing_up),
            getText(R.string.signing_up_text)
        )

        authViewModel.register(email, password) { success ->
            progressDialog.dismiss()
            if (success) {
                startLoading()
                Toast.makeText(
                    this,
                    R.string.signup_success,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    R.string.signup_failed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setLocale(languageCode: String): Boolean {
        if (languageCode == Locale.getDefault().language) return false

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
        return true
    }
}