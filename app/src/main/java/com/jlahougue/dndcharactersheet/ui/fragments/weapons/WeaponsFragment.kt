package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.WeaponDetail
import com.jlahougue.dndcharactersheet.databinding.FragmentWeaponsBinding
import com.jlahougue.dndcharactersheet.extensions.observeNonNull
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.fragments.weapons.addDialog.AddWeaponsDialog
import com.jlahougue.dndcharactersheet.ui.fragments.weapons.addDialog.WeaponNameAdapter
import com.jlahougue.dndcharactersheet.ui.fragments.weapons.details.PropertyDetailsDialog
import com.jlahougue.dndcharactersheet.ui.fragments.weapons.details.WeaponDetailsDialog
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class WeaponsFragment : Fragment(),
    WeaponAdapter.WeaponListener,
    WeaponDetailsDialog.DialogWeaponDetailsListener,
    AddWeaponsDialog.Companion.DialogAddWeaponListener,
    WeaponNameAdapter.Companion.WeaponNameListener {

    private var _binding: FragmentWeaponsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val main: MainActivity by lazy { activity as MainActivity }

    private val weaponsViewModel: WeaponsViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(WeaponsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeaponsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.buttonAddWeapon.setOnClickListener {
            weaponsViewModel.getNotOwnedWeapons {
                val dialog = AddWeaponsDialog(it, this, this)
                main.runOnUiThread {
                    dialog.show(parentFragmentManager, AddWeaponsDialog.TAG)
                }
            }
        }

        val weaponAdapter = WeaponAdapter(this)
        binding.recyclerWeapons.adapter = weaponAdapter

        main.mainViewModel.characterID.observeOnce(viewLifecycleOwner) {
            weaponsViewModel.characterID = it

            binding.viewModel = weaponsViewModel

            weaponsViewModel.weapons.observeNonNull(viewLifecycleOwner) { weapons ->
                weaponAdapter.weapons = weapons
            }

            main.mainViewModel.preferences.observeNonNull(viewLifecycleOwner) { preferences ->
                weaponAdapter.unitSystem = preferences.unitSystem
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onWeaponClicked(weapon: String) {
        weaponsViewModel.getWeapon(weapon, this::openWeaponDetails)
    }

    private fun openWeaponDetails(weapon: WeaponDetail) {
        val dialog = WeaponDetailsDialog(weapon, this)
        main.runOnUiThread {
            dialog.show(parentFragmentManager, WeaponDetailsDialog.TAG)
        }
    }

    override fun updateCharacterWeapon(weapon: WeaponDetail) {
        weaponsViewModel.updateCharacterWeapon(weapon)
    }

    override fun openWeaponPropertyDetails(property: Property) {
        val dialog = PropertyDetailsDialog(property)
        main.runOnUiThread {
            dialog.show(parentFragmentManager, PropertyDetailsDialog.TAG)
        }
    }

    override fun addWeapons(weaponCounts: Map<String, Int>) {
        weaponsViewModel.addWeapons(weaponCounts)
    }
}