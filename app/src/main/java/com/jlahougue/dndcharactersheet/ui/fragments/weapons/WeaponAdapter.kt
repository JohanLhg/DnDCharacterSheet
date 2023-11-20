package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.enums.UnitSystem
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView
import com.jlahougue.dndcharactersheet.databinding.RecyclerWeaponBinding

class WeaponAdapter(private val listener: WeaponListener) : RecyclerView.Adapter<WeaponAdapter.ViewHolder>() {

    interface WeaponListener {
        fun onWeaponClicked(weapon: String)
    }

    class ViewHolder(val bind: RecyclerWeaponBinding) : RecyclerView.ViewHolder(bind.root)

    var weapons = listOf<WeaponView>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var unitSystem = UnitSystem.METRIC
        set(value) {
            field = value
            notifyItemRangeChanged(0, weapons.size, UNIT_SYSTEM_CHANGED)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerWeaponBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = weapons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weapon = weapons[position]

        holder.bind.weapon = weapon
        holder.bind.unitSystem = unitSystem

        holder.bind.layoutWeapon.setOnClickListener {
            listener.onWeaponClicked(weapons[holder.adapterPosition].name)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(UNIT_SYSTEM_CHANGED) -> holder.bind.unitSystem = unitSystem
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    companion object {
        const val UNIT_SYSTEM_CHANGED = 0
    }
}