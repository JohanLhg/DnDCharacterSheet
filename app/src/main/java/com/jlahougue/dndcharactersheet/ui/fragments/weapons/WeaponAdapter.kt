package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerWeaponBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = weapons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weapon = weapons[position]

        holder.bind.weapon = weapon

        holder.bind.layoutWeapon.setOnClickListener {
            listener.onWeaponClicked(weapons[holder.adapterPosition].name)
        }
    }
}