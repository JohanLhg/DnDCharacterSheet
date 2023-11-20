package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.databinding.RecyclerSkillSavingThrowBinding

class SavingThrowAdapter(private val listener: OnAbilityChangedListener) : RecyclerView.Adapter<SkillSavingThrowViewHolder>() {

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

    interface OnAbilityChangedListener {
        fun onAbilityProficiencyChanged(ability: AbilityView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillSavingThrowViewHolder {
        return SkillSavingThrowViewHolder(RecyclerSkillSavingThrowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = abilities.size

    override fun onBindViewHolder(holder: SkillSavingThrowViewHolder, position: Int) {
        val context = holder.itemView.context
        val ability = abilities[position]

        holder.bind.checkProficiency.setOnCheckedChangeListener { _, _ ->  }

        holder.bind.checkProficiency.isChecked = ability.proficiency
        holder.bind.textName.text = ability.name.getName(context)
        holder.bind.textModifier.text = context.getString(R.string.plus_value, ability.modifier)

        holder.bind.checkProficiency.setOnCheckedChangeListener { _, isChecked ->
            changeProficiency(holder.adapterPosition, isChecked)
        }
    }

    override fun onBindViewHolder(
        holder: SkillSavingThrowViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when {
            payloads.contains(MODIFIER) -> holder.bind.textModifier.text = holder.itemView.context.getString(R.string.plus_value, abilities[position].modifier)
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun changeProficiency(position: Int, isChecked: Boolean) {
        val ability = abilities[position]
        ability.proficiency = isChecked
        listener.onAbilityProficiencyChanged(ability)
    }
}