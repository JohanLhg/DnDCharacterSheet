package com.jlahougue.dndcharactersheet.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.databinding.FragmentProfileBinding
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class ProfileFragment : Fragment() {

    val main: MainActivity by lazy { activity as MainActivity }

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(ProfileViewModel::class.java)
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        if (result != null) {
            profileViewModel.updateProfileImage(result)
            binding.imageProfile.setImageURI(result)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).mainViewModel.characterID.observe(viewLifecycleOwner) {
            if (it == 0L) return@observe
            profileViewModel.characterID = it
            profileViewModel.loadCharacterImage(main, binding.imageProfile)
        }

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        Glide.with(main)
            .load(R.drawable.loading)
            .centerInside()
            .into(binding.imageProfile)

        binding.imageProfile.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.buttonoLevelUp.setOnClickListener {
            var level = binding.editLevel.text.toString().toIntOrNull() ?: 0
            level++
            binding.editLevel.setText((level).toString())
        }

        profileViewModel.character.observeOnce(viewLifecycleOwner) {
            binding.editName.setText(it.name)
            binding.editRace.setText(it.race)
            binding.editClass.setText(it.charClass)
            binding.editLevel.setText(it.level.toString())
            binding.editGender.setText(it.gender)
            binding.editAge.setText(it.age.toString())
            binding.editHeight.setText(it.height.toString())
            binding.editWeight.setText(it.weight.toString())

            binding.editPersonalityTraits.setText(it.personality)
            binding.editIdeals.setText(it.ideals)
            binding.editBonds.setText(it.bonds)
            binding.editFlaws.setText(it.flaws)

            binding.editBackgroundTitle.setText(it.backgroundTitle)
            binding.editBackground.setText(it.background)

            initializeListeners()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeListeners() {
        binding.editName.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.name = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editRace.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.race = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editClass.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.charClass = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editLevel.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.level = it.toString().toIntOrNull() ?: 0
            profileViewModel.updateCharacter(character)
        }
        binding.editGender.addTextChangedListener{
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.gender = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editAge.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.age = it.toString().toIntOrNull() ?: 0
            profileViewModel.updateCharacter(character)
        }
        binding.editHeight.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.height = it.toString().toDoubleOrNull() ?: 0.0
            profileViewModel.updateCharacter(character)
        }
        binding.editWeight.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.weight = it.toString().toIntOrNull() ?: 0
            profileViewModel.updateCharacter(character)
        }

        binding.editPersonalityTraits.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.personality = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editIdeals.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.ideals = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editBonds.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.bonds = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editFlaws.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.flaws = it.toString()
            profileViewModel.updateCharacter(character)
        }

        binding.editBackgroundTitle.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.backgroundTitle = it.toString()
            profileViewModel.updateCharacter(character)
        }
        binding.editBackground.addTextChangedListener {
            val character = profileViewModel.character.value ?: return@addTextChangedListener
            character.background = it.toString()
            profileViewModel.updateCharacter(character)
        }
    }
}