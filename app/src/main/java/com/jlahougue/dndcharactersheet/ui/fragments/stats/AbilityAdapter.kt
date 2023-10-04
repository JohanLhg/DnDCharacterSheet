package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.databinding.RecyclerAbilityBinding

class AbilityAdapter(private val listener: OnAbilityChangedListener) : RecyclerView.Adapter<AbilityAdapter.ViewHolder>() {

    companion object {
        const val MODIFIER = 0
    }

    var abilities = listOf<Ability>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val bind: RecyclerAbilityBinding) : RecyclerView.ViewHolder(bind.root)

    interface OnAbilityChangedListener {
        fun onAbilityChanged(ability: Ability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerAbilityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = abilities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ability = abilities[position]

        holder.bind.textAbilityName.text = ability.getName(holder.itemView.context)
        holder.bind.textAbilityModifier.text = ability.getModifier().toString()
        holder.bind.editAbilityValue.setText(ability.value.toString())

        holder.bind.editAbilityValue.addTextChangedListener { text ->
            val value = text.toString().toIntOrNull() ?: 0
            abilities[holder.adapterPosition].value = value
            listener.onAbilityChanged(ability)
            notifyItemChanged(holder.adapterPosition, MODIFIER)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(MODIFIER) -> {
                val ability = abilities[position]
                holder.bind.textAbilityModifier.text = ability.getModifier().toString()
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }
}