package com.jlahougue.dndcharactersheet.ui.fragments.stats

import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.databinding.RecyclerSkillSavingThrowBinding

class SkillSavingThrowViewHolder(val bind: RecyclerSkillSavingThrowBinding) : RecyclerView.ViewHolder(bind.root) {
    init {
        this.setIsRecyclable(false)
    }
}
