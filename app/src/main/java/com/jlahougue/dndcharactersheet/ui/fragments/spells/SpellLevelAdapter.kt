package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView
import com.jlahougue.dndcharactersheet.databinding.RecyclerCantripsFilterBinding
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellLevelFilterBinding

class SpellLevelAdapter(private val listener: SpellLevelListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_CANTRIPS = 0
        const val VIEW_TYPE_SPELL_LEVEL = 1

        const val ACTIVE = 0
    }

    interface SpellLevelListener {
        fun onSpellLevelClick(position: Int)
    }

    var characterLevel = 0

    var spellLevels = listOf<SpellSlotView>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var activeLevel = 0
        set(value) {
            notifyItemChanged(field, ACTIVE)
            field = value
            notifyItemChanged(field, ACTIVE)
        }

    class SpellLevelViewHolder(val bind: RecyclerSpellLevelFilterBinding) : RecyclerView.ViewHolder(bind.root)

    class CantripsViewHolder(val bind: RecyclerCantripsFilterBinding) : RecyclerView.ViewHolder(bind.root)

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_CANTRIPS else VIEW_TYPE_SPELL_LEVEL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CANTRIPS -> CantripsViewHolder(RecyclerCantripsFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> SpellLevelViewHolder(RecyclerSpellLevelFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount() = spellLevels.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val spellLevel = spellLevels[position]

        when (holder) {
            is CantripsViewHolder -> {
                holder.bind.active = position == activeLevel
                holder.bind.root.setOnClickListener {
                    listener.onSpellLevelClick(holder.adapterPosition)
                }
            }
            is SpellLevelViewHolder -> {
                holder.bind.spellSlot = spellLevel
                holder.bind.active = position == activeLevel
                holder.bind.root.setOnClickListener {
                    listener.onSpellLevelClick(holder.adapterPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when {
            payloads.contains(ACTIVE) -> {
                when (holder) {
                    is CantripsViewHolder -> {
                        holder.bind.active = position == activeLevel
                    }
                    is SpellLevelViewHolder -> {
                        holder.bind.active = position == activeLevel
                    }
                }
            }
            else -> onBindViewHolder(holder, position)
        }
    }
}