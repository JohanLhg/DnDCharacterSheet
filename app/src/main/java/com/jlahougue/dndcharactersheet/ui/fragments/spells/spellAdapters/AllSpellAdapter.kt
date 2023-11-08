package com.jlahougue.dndcharactersheet.ui.fragments.spells.spellAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.dal.repositories.CharacterSpellRepository
import com.jlahougue.dndcharactersheet.databinding.RecyclerCantripsBinding
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellLevelBinding

class AllSpellAdapter(private val spellListener: SpellAdapter.Companion.SpellListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var characterLevel = 0

    var spellLevels = mapOf<Int, List<SpellWithCharacterInfo>>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && spellLevels.containsKey(0)) VIEW_TYPE_CANTRIPS else VIEW_TYPE_SPELL_LEVEL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CANTRIPS -> CantripsViewHolder(RecyclerCantripsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> SpellLevelViewHolder(RecyclerSpellLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount(): Int {
        return spellLevels.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val levels = spellLevels.keys.toList().sorted()
        val level = levels[position]
        val spells = spellLevels[level] ?: listOf()

        when (holder) {
            is CantripsViewHolder -> holder.bind.apply {
                textSpellLevel.text = position.toString()
                recyclerSpells.adapter = SpellAdapter(spellListener, spells, false)
                divider.visibility = if (position == 0) ViewGroup.GONE else ViewGroup.VISIBLE
            }
            is SpellLevelViewHolder -> holder.bind.apply {
                textSpellLevel.text = level.toString()
                textSpellLevelMax.text = CharacterSpellRepository.getSpellSlotsForLevel(characterLevel, level).toString()
                recyclerSpells.adapter = SpellAdapter(spellListener, spells, false)
                divider.visibility = if (position == 0) ViewGroup.GONE else ViewGroup.VISIBLE
            }
        }
    }

    companion object {
        const val VIEW_TYPE_CANTRIPS = 0
        const val VIEW_TYPE_SPELL_LEVEL = 1

        class SpellLevelViewHolder(val bind: RecyclerSpellLevelBinding) : RecyclerView.ViewHolder(bind.root)
        class CantripsViewHolder(val bind: RecyclerCantripsBinding) : RecyclerView.ViewHolder(bind.root)
    }
}