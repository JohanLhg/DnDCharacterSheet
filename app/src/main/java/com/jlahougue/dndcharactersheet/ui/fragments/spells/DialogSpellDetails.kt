package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.utilityClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.DialogSpellDetailsBinding

class DialogSpellDetails(
    private val spell: SpellWithCharacterInfo,
    private val listener: DialogSpellDetailsListener
) : DialogFragment() {

    interface DialogSpellDetailsListener {
        fun updateCharacterSpell(characterSpell: CharacterSpell)
        fun onSpellDetailsClosed()
    }

    companion object {
        const val TAG = "DialogSpellDetails"
    }

    private lateinit var dialogBinding: DialogSpellDetailsBinding

    private var spellChanged = false

    //Make dialog fullscreen
    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        //get screen width
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        //set dialog width
        window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = DialogSpellDetailsBinding.inflate(inflater, container, false)

        dialogBinding.spell = spell

        dialogBinding.checkBoxSpellUnlocked.setOnCheckedChangeListener { _, isChecked ->
            spell.setUnlocked(isChecked)
            updateSpell()
        }

        dialogBinding.checkBoxSpellPrepared.setOnCheckedChangeListener { _, isChecked ->
            spell.prepared = isChecked
            updateSpell()
        }

        dialogBinding.checkBoxSpellHighlighted.setOnCheckedChangeListener { _, isChecked ->
            spell.highlighted = isChecked
            updateSpell()
        }

        return dialogBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        if (spellChanged)
            listener.onSpellDetailsClosed()
    }

    private fun updateSpell() {
        dialogBinding.spell = spell
        listener.updateCharacterSpell(spell.getCharacterSpell())
        spellChanged = true
    }
}