package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.databinding.RecyclerListCommaBinding

class WeaponPropertyAdapter(private val properties: List<Property>, private val listener: PropertyListener) : RecyclerView.Adapter<WeaponPropertyAdapter.ViewHolder>() {

    interface PropertyListener {
        fun onPropertyClicked(property: Property)
    }

    class ViewHolder(val bind: RecyclerListCommaBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerListCommaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = properties.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = properties[position]

        holder.bind.textName.text = property.name
        holder.bind.textComma.visibility =
            if (position == properties.size - 1) GONE
            else VISIBLE

        holder.bind.root.setOnClickListener {
            listener.onPropertyClicked(properties[holder.adapterPosition])
        }
    }
}