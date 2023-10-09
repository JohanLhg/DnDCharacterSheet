package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository.Companion.getMaxSpellLevel
import com.jlahougue.dndcharactersheet.databinding.RecyclerCantripsBinding
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellLevelBinding

class SpellLevelAdapter(private val spellListener: SpellAdapter.SpellListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_CANTRIPS = 0
        const val VIEW_TYPE_SPELL_LEVEL = 1
    }

    var characterLevel = 0
    var editMode = false

    var spellLevels = mapOf<Int, List<SpellWithCharacterInfo>>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SpellLevelViewHolder(val bind: RecyclerSpellLevelBinding) : RecyclerView.ViewHolder(bind.root)

    class CantripsViewHolder(val bind: RecyclerCantripsBinding) : RecyclerView.ViewHolder(bind.root)

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_CANTRIPS else VIEW_TYPE_SPELL_LEVEL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CANTRIPS -> CantripsViewHolder(RecyclerCantripsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> SpellLevelViewHolder(RecyclerSpellLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount(): Int {
        //if (spellLevels.isEmpty()) return 0
        //var max = 0
        //spellLevels.keys.forEach { if (it > max) max = it }
        return getMaxSpellLevel(characterLevel) + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val spells = spellLevels[position] ?: listOf()

        when (holder) {
            is CantripsViewHolder -> {
                holder.bind.textSpellLevel.text = position.toString()
                holder.bind.recyclerSpells.adapter = SpellAdapter(editMode, spells, spellListener)
            }
            is SpellLevelViewHolder -> {
                holder.bind.textSpellLevel.text = position.toString()
                holder.bind.textSpellLevelMax.text = CharacterSpellRepository.getSpellSlotsForLevel(characterLevel, position).toString()

                holder.bind.recyclerSpells.adapter = SpellAdapter(editMode, spells, spellListener)
            }
        }
    }
}