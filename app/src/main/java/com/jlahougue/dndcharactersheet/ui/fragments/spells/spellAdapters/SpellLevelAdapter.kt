package com.jlahougue.dndcharactersheet.ui.fragments.spells.spellAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellLevel
import com.jlahougue.dndcharactersheet.databinding.RecyclerCantripsBinding
import com.jlahougue.dndcharactersheet.databinding.RecyclerSpellLevelBinding

class SpellLevelAdapter(
    private val spellLevelListener: SpellLevelListener,
    private val spellListener: SpellAdapter.Companion.SpellListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var levels = listOf<SpellLevel>()
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
                else -> {
                    val oldMapped = mutableMapOf<Int, SpellLevel>()
                    old.forEach { oldMapped[it.spellSlot.level] = it }

                    field.forEachIndexed { index, level ->
                        // When this level was already in the previous list
                        if (oldMapped.containsKey(level.spellSlot.level)) {
                            val oldLevel = oldMapped[level.spellSlot.level]!!

                            when {
                                oldLevel.spellSlot.left != level.spellSlot.left
                                        && oldLevel.spells != level.spells ->
                                    notifyItemChanged(index)

                                oldLevel.spellSlot.left != level.spellSlot.left ->
                                    notifyItemChanged(index, PAYLOAD_SLOT)

                                oldLevel.spells != level.spells ->
                                    notifyItemChanged(index, PAYLOAD_SPELLS)
                            }
                        }
                        // When this level was not in the previous list
                        else notifyItemInserted(index)
                    }
                }
            }
        }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && levels[0].spellSlot.level == 0) VIEW_TYPE_CANTRIPS
        else VIEW_TYPE_SPELL_LEVEL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CANTRIPS -> CantripsViewHolder(RecyclerCantripsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> SpellLevelViewHolder(RecyclerSpellLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount() = levels.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val level = levels[position]
        val spells = level.spells

        when (holder) {
            is CantripsViewHolder -> holder.bind.apply {
                textSpellLevel.text = position.toString()
                recyclerSpells.adapter = SpellAdapter(spellListener, spells, false)
                divider.visibility = if (position % 2 == 0) ViewGroup.GONE else ViewGroup.VISIBLE
            }
            is SpellLevelViewHolder -> holder.bind.apply {
                spellSlot = level.spellSlot
                recyclerSpells.adapter = SpellAdapter(spellListener, spells, false)
                divider.visibility = if (position % 2 == 0) ViewGroup.GONE else ViewGroup.VISIBLE

                buttonSlotMinus.setOnClickListener {
                    level.spellSlot.left--
                    spellLevelListener.updateSpellSlot(level.spellSlot.getSpellSlot())
                }

                buttonSlotPlus.setOnClickListener {
                    level.spellSlot.left++
                    spellLevelListener.updateSpellSlot(level.spellSlot.getSpellSlot())
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
            payloads.contains(PAYLOAD_SLOT) -> {
                if (holder is SpellLevelViewHolder)
                    holder.bind.spellSlot = levels[position].spellSlot
            }
            payloads.contains(PAYLOAD_SPELLS) -> {
                if (holder is SpellLevelViewHolder) {
                    holder.bind.recyclerSpells.adapter = SpellAdapter(
                        spellListener,
                        levels[position].spells,
                        false
                    )
                }
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    companion object {
        const val VIEW_TYPE_CANTRIPS = 0
        const val VIEW_TYPE_SPELL_LEVEL = 1

        // PAYLOADS
        const val PAYLOAD_SLOT = 0
        const val PAYLOAD_SPELLS = 1

        interface SpellLevelListener {
            fun updateSpellSlot(spellSlot: SpellSlot)
        }

        class SpellLevelViewHolder(val bind: RecyclerSpellLevelBinding)
            : RecyclerView.ViewHolder(bind.root)
        class CantripsViewHolder(val bind: RecyclerCantripsBinding)
            : RecyclerView.ViewHolder(bind.root)
    }
}