package com.jlahougue.dndcharactersheet.ui.fragments.spells.unlockedSpellsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellBinding

class SpellAdapter(
    private val editMode: Boolean,
    var spells: List<SpellWithCharacterInfo>,
    private val spellListener: SpellListener
) : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {

    interface SpellListener {
        fun onSpellClick(spell: SpellWithCharacterInfo)
        fun updateCharacterSpell(characterSpell: CharacterSpell)
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
        holder.bind.editMode = editMode

        holder.bind.layoutSpell.setOnClickListener {
            spellListener.onSpellClick(spells[holder.adapterPosition])
        }

        holder.bind.checkBoxSpellUnlocked.setOnCheckedChangeListener { _, isChecked ->
            val currentSpell = spells[holder.adapterPosition]
            currentSpell.setUnlocked(isChecked)
            spellListener.updateCharacterSpell(currentSpell.getCharacterSpell())
        }

        holder.bind.checkBoxSpellPrepared.setOnCheckedChangeListener { _, isChecked ->
            val currentSpell = spells[holder.adapterPosition]
            currentSpell.prepared = isChecked
            spellListener.updateCharacterSpell(currentSpell.getCharacterSpell())
        }

        holder.bind.buttonHighlight.setOnClickListener {
            val currentSpell = spells[holder.adapterPosition]
            currentSpell.highlighted = !currentSpell.highlighted
            holder.bind.spell = currentSpell
            spellListener.updateCharacterSpell(currentSpell.getCharacterSpell())
        }
    }
}