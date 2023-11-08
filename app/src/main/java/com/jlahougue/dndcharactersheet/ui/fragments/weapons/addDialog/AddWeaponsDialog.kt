package com.jlahougue.dndcharactersheet.ui.fragments.weapons.addDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.databinding.DialogAddWeaponsBinding

class AddWeaponsDialog(
    private val weapons: List<String>,
    private val listener: DialogAddWeaponListener,
    private val adapterListener: WeaponNameAdapter.WeaponNameListener
) : DialogFragment() {

    companion object {
        const val TAG = "DialogAddWeapon"
    }

    interface DialogAddWeaponListener {
        fun addWeapons(weaponCounts: Map<String, Int>)
    }

    private lateinit var dialogBinding: DialogAddWeaponsBinding

    //Make dialog fullscreen
    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = DialogAddWeaponsBinding.inflate(inflater, container, false)

        val adapter = WeaponNameAdapter(weapons, adapterListener)
        dialogBinding.recyclerWeaponNames.adapter = adapter

        dialogBinding.buttonAddWeapon.setOnClickListener {
            val map = adapter.weaponCounts.filter { it.value > 0 }
            listener.addWeapons(map)
        }

        dialogBinding.buttonCancel.setOnClickListener { dismiss() }

        dialogBinding.root.setOnClickListener { dismiss() }

        return dialogBinding.root
    }
}