package com.jlahougue.dndcharactersheet.ui.fragments.weapons.addDialog

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.databinding.RecyclerWeaponNameBinding

class WeaponNameAdapter(
    weapons: List<String>,
    private val listener: WeaponNameListener
) : RecyclerView.Adapter<WeaponNameAdapter.ViewHolder>() {

    var weapons = weapons
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val weaponCounts = mutableMapOf<String, Int>()

    class ViewHolder(val bind: RecyclerWeaponNameBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerWeaponNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = weapons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weapon = weapons[position]

        holder.bind.textWeaponName.text = weapon
        holder.bind.divider.visibility =
            if (position == weapons.size - 1) GONE
            else VISIBLE

        setCountUI(holder, weapon)

        holder.bind.buttonCountMinus.setOnClickListener {
            weaponCounts[weapon] = weaponCounts[weapon]?.minus(1)?: 0
            setCountUI(holder, weapon)
        }

        holder.bind.buttonCountPlus.setOnClickListener {
            weaponCounts[weapon] = weaponCounts[weapon]?.plus(1)?: 1
            setCountUI(holder, weapon)
        }

        holder.bind.root.setOnClickListener {
            listener.onWeaponClicked(weapon)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(COUNT) -> setCountUI(holder, weapons[position])
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun setCountUI(holder: ViewHolder, weapon: String) {
        val context = holder.itemView.context
        val count = weaponCounts[weapon]?: 0
        holder.bind.textCount.text = count.toString()
        if (count == 0) holder.bind.root.setBackgroundColor(context.getColor(R.color.transparent))
        else holder.bind.root.setBackgroundColor(context.getColor(R.color.spell_transparent))
        holder.bind.buttonCountMinus.visibility = if (count == 0) GONE else VISIBLE
    }

    companion object {
        const val COUNT = 0

        interface WeaponNameListener {
            fun onWeaponClicked(weapon: String)
        }
    }
}