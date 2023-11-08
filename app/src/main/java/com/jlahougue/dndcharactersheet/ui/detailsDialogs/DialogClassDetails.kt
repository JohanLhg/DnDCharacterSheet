package com.jlahougue.dndcharactersheet.ui.detailsDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.repositories.AbilityRepository
import com.jlahougue.dndcharactersheet.databinding.DialogClassDetailsBinding
import io.noties.markwon.Markwon

class DialogClassDetails(
    private val clazz: Class
) : DialogFragment() {

    companion object {
        const val TAG = "DialogClassDetails"
    }

    private lateinit var dialogBinding: DialogClassDetailsBinding

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
        dialogBinding = DialogClassDetailsBinding.inflate(inflater, container, false)

        dialogBinding.context = context
        dialogBinding.ability = AbilityRepository.Companion
        dialogBinding.markwon = Markwon.create(requireContext())
        dialogBinding.clazz = clazz

        dialogBinding.root.setOnClickListener { dismiss() }

        return dialogBinding.root
    }
}