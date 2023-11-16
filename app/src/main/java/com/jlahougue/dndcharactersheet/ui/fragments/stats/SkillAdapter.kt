package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.views.SkillView
import com.jlahougue.dndcharactersheet.dal.repositories.SkillRepository
import com.jlahougue.dndcharactersheet.databinding.RecyclerSkillSavingThrowBinding

class SkillAdapter(private val listener: OnSkillChangedListener) : RecyclerView.Adapter<SkillSavingThrowViewHolder>() {

    var skills = listOf<SkillView>()
        set(value) {
            field = value
            filter(search)
        }

    private var filteredSkills = listOf<SkillView>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var search = ""
        set(value) {
            field = value
            filter(value)
        }

    interface OnSkillChangedListener {
        fun onSkillProficiencyChanged(skill: SkillView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillSavingThrowViewHolder {
        return SkillSavingThrowViewHolder(RecyclerSkillSavingThrowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = filteredSkills.size

    override fun onBindViewHolder(holder: SkillSavingThrowViewHolder, position: Int) {
        val context = holder.itemView.context
        val skill = filteredSkills[position]

        holder.bind.checkProficiency.isChecked = skill.proficiency
        holder.bind.textName.text = SkillRepository.getFullName(context, skill.name, skill.modifierType)
        holder.bind.textModifier.text = context.getString(R.string.plus_value, skill.modifier)

        holder.bind.checkProficiency.setOnCheckedChangeListener { _, isChecked ->
            changeProficiency(holder.adapterPosition, isChecked)
        }
    }

    private fun changeProficiency(position: Int, isChecked: Boolean) {
        filteredSkills[position].proficiency = isChecked
        listener.onSkillProficiencyChanged(filteredSkills[position])
    }

    fun filter(search: String) {
        filteredSkills = skills.filter { it.name.contains(search, true) }
    }
}