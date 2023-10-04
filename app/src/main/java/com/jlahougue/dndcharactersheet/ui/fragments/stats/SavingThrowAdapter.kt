package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.room.views.AbilityModifierView
import com.jlahougue.dndcharactersheet.databinding.RecyclerSkillSavingThrowBinding

class SavingThrowAdapter(private val listener: OnAbilityChangedListener) : RecyclerView.Adapter<SkillSavingThrowViewHolder>() {

    var abilities = listOf<AbilityModifierView>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnAbilityChangedListener {
        fun onAbilityChanged(ability: AbilityModifierView)
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
        holder.bind.textName.text = ability.getName(context)
        holder.bind.textModifier.text = context.getString(R.string.plus_value, ability.modifier)

        holder.bind.checkProficiency.setOnCheckedChangeListener { _, isChecked ->
            changeProficiency(holder.adapterPosition, isChecked)
        }
    }

    private fun changeProficiency(position: Int, isChecked: Boolean) {
        val ability = abilities[position]
        ability.proficiency = isChecked
        listener.onAbilityChanged(ability)
    }
}