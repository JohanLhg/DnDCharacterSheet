package com.jlahougue.dndcharactersheet.ui.fragments.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.databinding.FragmentInventoryBinding
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val inventoryViewModel: InventoryViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(InventoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)

        (activity as MainActivity).mainViewModel.characterID.observe(viewLifecycleOwner) {
            if (it == 0L) return@observe
            inventoryViewModel.characterID = it
        }

        inventoryViewModel.notes.observeOnce(viewLifecycleOwner) { notes ->
            binding.editNotes.setText(notes)
            binding.editNotes.addTextChangedListener {
                inventoryViewModel.updateNotes(it.toString())
            }
        }

        inventoryViewModel.quests.observeOnce(viewLifecycleOwner) { quests ->
            binding.editQuests.setText(quests)
            binding.editQuests.addTextChangedListener {
                inventoryViewModel.updateQuests(it.toString())
            }
        }

        inventoryViewModel.money.observeOnce(viewLifecycleOwner) {
            binding.editCopperPieces.setText(it.copperPieces.toString())
            binding.editSilverPieces.setText(it.silverPieces.toString())
            binding.editElectrumPieces.setText(it.electrumPieces.toString())
            binding.editGoldPieces.setText(it.goldPieces.toString())
            binding.editPlatinumPieces.setText(it.platinumPieces.toString())
            binding.editOtherCurrencies.setText(it.otherCurrencies)

            binding.editCopperPieces.addTextChangedListener { updateMoney() }
            binding.editSilverPieces.addTextChangedListener { updateMoney() }
            binding.editElectrumPieces.addTextChangedListener { updateMoney() }
            binding.editGoldPieces.addTextChangedListener { updateMoney() }
            binding.editPlatinumPieces.addTextChangedListener { updateMoney() }
            binding.editOtherCurrencies.addTextChangedListener { updateMoney() }
        }

        inventoryViewModel.equipment.observeOnce(viewLifecycleOwner) { equipments ->
            binding.editEquipment.setText(equipments)
            binding.editEquipment.addTextChangedListener {
                inventoryViewModel.updateEquipment(it.toString())
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateMoney() {
        inventoryViewModel.updateMoney(
            binding.editCopperPieces.text.toString().toInt(),
            binding.editSilverPieces.text.toString().toInt(),
            binding.editElectrumPieces.text.toString().toInt(),
            binding.editGoldPieces.text.toString().toInt(),
            binding.editPlatinumPieces.text.toString().toInt(),
            binding.editOtherCurrencies.text.toString()
        )
    }
}