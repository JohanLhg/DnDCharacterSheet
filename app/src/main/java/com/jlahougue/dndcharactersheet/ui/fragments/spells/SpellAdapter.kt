package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellBinding

class SpellAdapter(var spells: List<SpellWithCharacterInfo>) : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {

    class ViewHolder(val bind: RecyclerSpellBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerSpellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = spells.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = spells[position]

        holder.bind.spell = spell
    }
}