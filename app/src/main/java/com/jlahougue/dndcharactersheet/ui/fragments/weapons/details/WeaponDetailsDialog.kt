package com.jlahougue.dndcharactersheet.ui.fragments.weapons.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.WeaponDetail
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.databinding.DialogWeaponDetailsBinding

class WeaponDetailsDialog(
    private val weapon: WeaponDetail,
    private val listener: DialogWeaponDetailsListener
) : DialogFragment(), WeaponPropertyAdapter.PropertyListener {

    interface DialogWeaponDetailsListener {
        fun updateCharacterWeapon(weapon: WeaponDetail)
        fun openWeaponPropertyDetails(property: Property)
    }

    companion object {
        const val TAG = "DialogWeaponDetails"
    }

    private lateinit var dialogBinding: DialogWeaponDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = DialogWeaponDetailsBinding.inflate(inflater, container, false)

        dialogBinding.context = context
        dialogBinding.ability = AbilityRepository.Companion
        dialogBinding.weapon = weapon

        dialogBinding.recyclerProperties.adapter = WeaponPropertyAdapter(weapon.properties, this)

        dialogBinding.buttonCountMinus.setOnClickListener {
            if (weapon.count > 0) {
                weapon.count--
                updateWeapon()
            }
        }

        dialogBinding.buttonCountPlus.setOnClickListener {
            weapon.count++
            updateWeapon()
        }

        return dialogBinding.root
    }

    private fun updateWeapon() {
        dialogBinding.weapon = weapon
        listener.updateCharacterWeapon(weapon)
    }

    override fun onPropertyClicked(property: Property) {
        listener.openWeaponPropertyDetails(property)
    }
}