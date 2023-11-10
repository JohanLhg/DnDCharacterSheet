package com.jlahougue.dndcharactersheet.ui.fragments.weapons.addDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.databinding.DialogAddWeaponsBinding
import com.jlahougue.dndcharactersheet.ui.elements.SearchBarListener

class AddWeaponsDialog(
    private val weapons: List<String>,
    private val listener: DialogAddWeaponListener,
    adapterListener: WeaponNameAdapter.Companion.WeaponNameListener
) : DialogFragment() {

    private lateinit var dialogBinding: DialogAddWeaponsBinding

    private val adapter = WeaponNameAdapter(weapons, adapterListener)

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

        dialogBinding.recyclerWeaponNames.adapter = adapter

        initializeSearchBar()

        dialogBinding.buttonAddWeapon.setOnClickListener {
            val map = adapter.weaponCounts.filter { it.value > 0 }
            listener.addWeapons(map)
            dismiss()
        }

        dialogBinding.buttonCancel.setOnClickListener { dismiss() }

        dialogBinding.root.setOnClickListener { dismiss() }

        return dialogBinding.root
    }

    private fun initializeSearchBar() {
        dialogBinding.includeSearch.apply {
            accentColor = requireActivity().getColor(R.color.accent)

            listener = object : SearchBarListener {
                override fun focusOnSearch() { editSearch.requestFocus() }
                override fun clearSearch() { editSearch.text.clear() }
            }

            editSearch.addTextChangedListener {
                val search = it.toString()
                adapter.weapons = weapons.filter { name ->
                    name.contains(search, true)
                }
            }

            editSearch.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                focused = hasFocus
            }
        }
    }

    companion object {
        const val TAG = "DialogAddWeapon"

        interface DialogAddWeaponListener {
            fun addWeapons(weaponCounts: Map<String, Int>)
        }
    }
}