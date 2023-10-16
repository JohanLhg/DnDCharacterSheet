package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellDamageBinding

class SpellDamageAdapter(
    private val spellDamage: List<SpellDamage>
) : RecyclerView.Adapter<SpellDamageAdapter.SpellDamageViewHolder>() {
    class SpellDamageViewHolder(val bind: RecyclerSpellDamageBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellDamageViewHolder {
        return SpellDamageViewHolder(RecyclerSpellDamageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = spellDamage.size

    override fun onBindViewHolder(holder: SpellDamageViewHolder, position: Int) {
        val damage = spellDamage[position]

        holder.bind.damage = damage
    }
}