package com.jlahougue.dndcharactersheet.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.databinding.DialogClassSelectionBinding

class DialogClassSelection(
    private val classes: List<Class>,
    private val listener: DialogClassSelectionListener,
    private val adapterListener: ClassAdapter.ClassListener
) : DialogFragment() {

    companion object {
        const val TAG = "DialogClassSelection"
    }

    interface DialogClassSelectionListener {
        fun setClass(clazz: Class)
    }

    private lateinit var dialogBinding: DialogClassSelectionBinding

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
        dialogBinding = DialogClassSelectionBinding.inflate(inflater, container, false)

        val adapter = ClassAdapter(classes, adapterListener) {
            dialogBinding.buttonSelectClass.isEnabled = it
        }
        dialogBinding.recyclerClasses.adapter = adapter

        dialogBinding.buttonSelectClass.setOnClickListener {
            val clazz = adapter.selectedClass ?: return@setOnClickListener
            listener.setClass(clazz)
            dismiss()
        }

        dialogBinding.buttonSelectClass.isEnabled = false

        dialogBinding.buttonCancel.setOnClickListener { dismiss() }

        dialogBinding.root.setOnClickListener { dismiss() }

        return dialogBinding.root
    }
}