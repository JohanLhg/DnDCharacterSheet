package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.room.views.SkillView
import com.jlahougue.dndcharactersheet.databinding.RecyclerSkillSavingThrowBinding

class SkillAdapter : RecyclerView.Adapter<SkillSavingThrowViewHolder>() {

    var skills = listOf<SkillView>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillSavingThrowViewHolder {
        return SkillSavingThrowViewHolder(RecyclerSkillSavingThrowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = skills.size

    override fun onBindViewHolder(holder: SkillSavingThrowViewHolder, position: Int) {
        val context = holder.itemView.context
        val skill = skills[position]

        holder.bind.checkProficiency.isChecked = skill.proficiency
        holder.bind.textSkillName.text = skill.getFullName(context)
        holder.bind.textSkillModifier.text = context.getString(R.string.plus_value, skill.modifier)
    }
}