package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.databinding.RecyclerAbilityBinding

class AbilityAdapter(private val listener: OnAbilityChangedListener) : RecyclerView.Adapter<AbilityAdapter.ViewHolder>() {

    companion object {
        const val MODIFIER = 0
    }

    var abilities = listOf<AbilityView>()
        set(value) {
            val oldField = field
            field = value
            if (oldField.isEmpty()) notifyItemRangeInserted(0, value.size)
            else notifyItemRangeChanged(0, value.size, MODIFIER)
        }

    class ViewHolder(val bind: RecyclerAbilityBinding) : RecyclerView.ViewHolder(bind.root) {
        init {
            this.setIsRecyclable(false)
        }
    }

    interface OnAbilityChangedListener {
        fun onAbilityValueChanged(ability: AbilityView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerAbilityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = abilities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val ability = abilities[position]

        holder.bind.textAbilityName.text = AbilityRepository.getName(context, ability.name)
        holder.bind.textAbilityModifier.text = context.getString(R.string.plus_value, abilities[position].modifier)
        holder.bind.editAbilityValue.setText(ability.value.toString())

        holder.bind.editAbilityValue.addTextChangedListener { text ->
            var value = text.toString().toIntOrNull()
            if (value == null) {
                value = 0
                holder.bind.editAbilityValue.setText(value.toString())
            }
            if (value == 0) {
                holder.bind.editAbilityValue.clearFocus()
                holder.bind.editAbilityValue.requestFocus()
            }
            abilities[holder.adapterPosition].value = value
            listener.onAbilityValueChanged(abilities[holder.adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(MODIFIER) -> {
                holder.bind.textAbilityModifier.text = holder.itemView.context.getString(
                    R.string.plus_value,
                    abilities[position].modifier
                )
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }
}