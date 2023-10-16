package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView
import com.jlahougue.dndcharactersheet.databinding.FragmentWeaponsBinding
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class WeaponsFragment : Fragment(), WeaponAdapter.WeaponListener {

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

        main.mainViewModel.characterID.observe(viewLifecycleOwner) {
            if (it == 0L) return@observe
            weaponsViewModel.characterID = it

            weaponsViewModel.weapons.observe(viewLifecycleOwner) { weapons ->
                if (weapons == null) return@observe
println(weapons.size)
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
        TODO("Not yet implemented")
    }
}