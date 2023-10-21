package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.WeaponDetail
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView
import com.jlahougue.dndcharactersheet.databinding.FragmentWeaponsBinding
import com.jlahougue.dndcharactersheet.extensions.observeNonNull
import com.jlahougue.dndcharactersheet.extensions.observeOnce
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class WeaponsFragment : Fragment(), WeaponAdapter.WeaponListener, DialogWeaponDetails.DialogWeaponDetailsListener {

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

        val weaponAdapter = WeaponAdapter(this)
        binding.recyclerWeapons.adapter = weaponAdapter

        main.mainViewModel.characterID.observeOnce(viewLifecycleOwner) {
            weaponsViewModel.characterID = it

            weaponsViewModel.weapons.observeNonNull(viewLifecycleOwner) { weapons ->
                weaponAdapter.weapons = weapons
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onWeaponClicked(weapon: WeaponView) {
        weaponsViewModel.getWeapon(weapon.name, this::openWeaponDetailsDialog)
    }

    private fun openWeaponDetailsDialog(weapon: WeaponDetail) {
        val dialog = DialogWeaponDetails(weapon, this)
        main.runOnUiThread {
            dialog.show(parentFragmentManager, DialogWeaponDetails.TAG)
        }
    }

    override fun updateCharacterWeapon(weapon: WeaponDetail) {
        weaponsViewModel.updateCharacterWeapon(weapon)
    }

    override fun onWeaponPropertyClicked(property: Property) {
        val dialog = DialogPropertyDetails(property)
        main.runOnUiThread {
            dialog.show(parentFragmentManager, DialogPropertyDetails.TAG)
        }
    }
}