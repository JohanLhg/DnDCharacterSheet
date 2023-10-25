package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellBinding

class SpellAdapter(private val spellListener: SpellListener) : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {

    interface SpellListener {
        fun onSpellClick(spell: SpellWithCharacterInfo)
        fun updateCharacterSpell(characterSpell: CharacterSpell)
    }

    var spells = listOf<SpellWithCharacterInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var editMode = false

    class ViewHolder(val bind: RecyclerSpellBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerSpellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = spells.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = spells[position]

        holder.bind.spell = spell
        holder.bind.editMode = editMode

        holder.bind.layoutSpell.setOnClickListener {
            spellListener.onSpellClick(spells[holder.adapterPosition])
        }

        holder.bind.checkBoxSpellUnlocked.setOnCheckedChangeListener { _, isChecked ->
            spell.setUnlocked(isChecked)
            spellListener.updateCharacterSpell(spell.getCharacterSpell())
        }

        holder.bind.checkBoxSpellPrepared.setOnCheckedChangeListener { _, isChecked ->
            spell.prepared = isChecked
            holder.bind.spell = spell
            spellListener.updateCharacterSpell(spell.getCharacterSpell())
        }

        holder.bind.buttonHighlight.setOnClickListener {
            spell.highlighted = !spell.highlighted
            holder.bind.spell = spell
            spellListener.updateCharacterSpell(spell.getCharacterSpell())
        }
    }
}