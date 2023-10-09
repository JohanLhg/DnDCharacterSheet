package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellBinding

class SpellAdapter(private val editMode: Boolean, var spells: List<SpellWithCharacterInfo>, private val spellListener: SpellListener) : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {

    companion object {
        const val EDIT_MODE = 0
    }

    interface SpellListener {
        fun onSpellClick(spell: SpellWithCharacterInfo)
        fun setSpellPrepared(spell: SpellWithCharacterInfo, prepared: Boolean)
        fun setSpellUnlocked(spell: SpellWithCharacterInfo, unlocked: Boolean)
    }

    class ViewHolder(val bind: RecyclerSpellBinding) : RecyclerView.ViewHolder(bind.root) {
        init {
            this.setIsRecyclable(false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerSpellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = spells.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = spells[position]

        holder.bind.spell = spell

        holder.bind.layoutSpell.setOnClickListener {
            spellListener.onSpellClick(spells[holder.adapterPosition])
        }

        if (editMode) {
            holder.bind.checkBoxSpellPrepared.visibility = ViewGroup.GONE
            holder.bind.checkBoxSpellUnlocked.visibility = ViewGroup.VISIBLE

            holder.bind.checkBoxSpellUnlocked.setOnCheckedChangeListener { _, isChecked ->
                spellListener.setSpellUnlocked(spells[holder.adapterPosition], isChecked)
            }
        } else {
            holder.bind.checkBoxSpellPrepared.visibility = ViewGroup.VISIBLE
            holder.bind.checkBoxSpellUnlocked.visibility = ViewGroup.GONE

            holder.bind.checkBoxSpellPrepared.setOnCheckedChangeListener { _, isChecked ->
                spellListener.setSpellPrepared(spells[holder.adapterPosition], isChecked)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(EDIT_MODE) -> {
                if (editMode) {
                    holder.bind.checkBoxSpellPrepared.visibility = ViewGroup.GONE
                    holder.bind.checkBoxSpellUnlocked.visibility = ViewGroup.VISIBLE
                } else {
                    holder.bind.checkBoxSpellPrepared.visibility = ViewGroup.VISIBLE
                    holder.bind.checkBoxSpellUnlocked.visibility = ViewGroup.GONE
                }
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }
}