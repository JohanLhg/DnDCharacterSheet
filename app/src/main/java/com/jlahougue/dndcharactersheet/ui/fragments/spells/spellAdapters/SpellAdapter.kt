package com.jlahougue.dndcharactersheet.ui.fragments.spells.spellAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellBinding

class SpellAdapter(
    private val spellListener: SpellListener,
    spells: List<SpellWithCharacterInfo> = listOf(),
    private var editMode: Boolean = true
)
    : RecyclerView.Adapter<SpellAdapter.Companion.ViewHolder>() {

    var spells = spells
        set(value) {
            val old = field
            field = value

            when {
                // When the list was empty
                old.isEmpty() -> notifyItemRangeInserted(0, value.size)
                // When the list is empty
                value.isEmpty() -> notifyItemRangeRemoved(0, old.size)
                // When the list size changed
                old.size != value.size -> {
                    notifyItemRangeRemoved(0, old.size)
                    notifyItemRangeInserted(0, value.size)
                }
                // When the list size didn't change
                else -> field.forEachIndexed { index, spell ->
                    if (spell.id == old[index].id) notifyItemChanged(index, PAYLOAD_SPELL)
                    else notifyItemChanged(index)
                }
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
            spellListener.onSpellClick(spell)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(PAYLOAD_SPELL) -> holder.bind.spell = spells[position]
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    companion object {
        // Payloads
        const val PAYLOAD_SPELL = 0

        interface SpellListener {
            fun onSpellClick(spell: SpellWithCharacterInfo)
            fun updateCharacterSpell(characterSpell: CharacterSpell)
        }

        class ViewHolder(val bind: RecyclerSpellBinding) : RecyclerView.ViewHolder(bind.root)
    }
}