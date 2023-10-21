package com.jlahougue.dndcharactersheet.ui.fragments.weapons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.databinding.DialogPropertyDetailsBinding
import io.noties.markwon.Markwon

class DialogPropertyDetails(
    private val property: Property
) : DialogFragment() {

    companion object {
        const val TAG = "DialogPropertyDetails"
    }

    private lateinit var dialogBinding: DialogPropertyDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = DialogPropertyDetailsBinding.inflate(inflater, container, false)

        dialogBinding.markwon = Markwon.create(requireContext())
        dialogBinding.property = property

        return dialogBinding.root
    }
}