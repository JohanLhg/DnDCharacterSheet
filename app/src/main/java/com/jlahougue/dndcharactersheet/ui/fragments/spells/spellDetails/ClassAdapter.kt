package com.jlahougue.dndcharactersheet.ui.fragments.spells.spellDetails

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.databinding.RecyclerListCommaBinding

class ClassAdapter(private val classes: List<Class>, private val listener: ClassListener) : RecyclerView.Adapter<ClassAdapter.ViewHolder>() {

    interface ClassListener {
        fun onClassClicked(clazz: Class)
    }

    class ViewHolder(val bind: RecyclerListCommaBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerListCommaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = classes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clazz = classes[position]

        holder.bind.textName.text = clazz.name
        holder.bind.textComma.visibility =
            if (position == classes.size - 1) GONE
            else VISIBLE

        holder.bind.root.setOnClickListener {
            listener.onClassClicked(classes[holder.adapterPosition])
        }
    }
}