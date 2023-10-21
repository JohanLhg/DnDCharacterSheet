package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.entities.displayClasses.SpellWithCharacterInfo
import com.jlahougue.dndcharactersheet.databinding.DialogSpellDetailsBinding
import com.jlahougue.dndcharactersheet.ui.fragments.spells.clazz.ClassAdapter
import io.noties.markwon.Markwon

class DialogSpellDetails(
    private val spell: SpellWithCharacterInfo,
    private val listener: DialogSpellDetailsListener
) : DialogFragment(), ClassAdapter.ClassListener {

    interface DialogSpellDetailsListener {
        fun updateCharacterSpell(characterSpell: CharacterSpell)
        fun onSpellDetailsClosed()
        fun onClassClicked(clazz: Class)
    }

    companion object {
        const val TAG = "DialogSpellDetails"
    }

    private lateinit var dialogBinding: DialogSpellDetailsBinding

    private val oldSpell = spell.copy()

    //Make dialog fullscreen
    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = DialogSpellDetailsBinding.inflate(inflater, container, false)

        dialogBinding.markwon = Markwon.create(requireContext())
        dialogBinding.spell = spell

        dialogBinding.recyclerSpellDamge.adapter = SpellDamageAdapter(spell.damages)

        dialogBinding.recyclerClasses.adapter = ClassAdapter(spell.classes, this)

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

        dialogBinding.root.setOnClickListener { dismiss() }

        return dialogBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        if (spell != oldSpell)
            listener.onSpellDetailsClosed()
    }

    private fun updateSpell() {
        dialogBinding.spell = spell
        listener.updateCharacterSpell(spell.getCharacterSpell())
    }

    override fun onClassClicked(clazz: Class) {
        listener.onClassClicked(clazz)
    }
}