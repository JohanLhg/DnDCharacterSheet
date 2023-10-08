package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.databinding.FragmentSpellsBinding
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class SpellsFragment : Fragment() {

    private var _binding: FragmentSpellsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val spellsViewModel: SpellsViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(SpellsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpellsBinding.inflate(inflater, container, false)

        (activity as MainActivity).mainViewModel.characterID.observe(viewLifecycleOwner) {
            if (it == 0L) return@observe
            spellsViewModel.characterID = it

            spellsViewModel.spellcasting.observeOnce(viewLifecycleOwner) { spellcasting ->
                binding.textSpellcastingAbility.text = AbilityRepository.getModifierName(requireContext(), spellcasting.ability)
                binding.textSpellSaveDC.text = spellcasting.saveDC.toString()
                binding.textSpellAttackBonus.text = spellcasting.attackBonus.toString()
            }

            spellsViewModel.characterLevel.observeOnce(viewLifecycleOwner) { characterLevel ->
                val spellLevelAdapter = SpellLevelAdapter(characterLevel)
                binding.recyclerSpellLevels.adapter = spellLevelAdapter

                spellsViewModel.spells.observe(viewLifecycleOwner) { spellLevels ->
                    if (spellLevels != null)
                        spellLevelAdapter.spellLevels = spellLevels
                }

                binding.buttonEdit.setOnClickListener {
                    spellsViewModel.setEditMode(true)
                    binding.buttonEdit.visibility = View.GONE
                    binding.layoutEditingButtons.visibility = View.VISIBLE
                }

                binding.buttonUndo.setOnClickListener {
                    spellsViewModel.setEditMode(false)
                    binding.buttonEdit.visibility = View.VISIBLE
                    binding.layoutEditingButtons.visibility = View.GONE
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}