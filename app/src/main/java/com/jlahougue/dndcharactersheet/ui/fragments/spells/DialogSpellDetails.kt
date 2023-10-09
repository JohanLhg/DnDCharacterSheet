package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.dal.entities.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.DialogSpellDetailsBinding

class DialogSpellDetails(private val spell: SpellWithCharacterInfo) : DialogFragment() {

    interface DialogSpellDetailsListener {
        fun setSpellPrepared(spell: SpellWithCharacterInfo, prepared: Boolean)
        fun setSpellUnlocked(spell: SpellWithCharacterInfo, unlocked: Boolean)
        fun setSpellHighlighted(spell: SpellWithCharacterInfo, highlighted: Boolean)
    }

    companion object {
        const val TAG = "DialogSpellDetails"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialogBinding = DialogSpellDetailsBinding.inflate(inflater, container, false)

        dialogBinding.spell = spell.spell

        return dialogBinding.root
    }
}