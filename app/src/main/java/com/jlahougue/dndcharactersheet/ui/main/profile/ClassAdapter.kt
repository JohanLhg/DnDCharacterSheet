package com.jlahougue.dndcharactersheet.ui.main.profile

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.databinding.RecyclerClassBinding

class ClassAdapter(
    private val classes: List<Class>,
    private val listener: ClassListener,
    private val onClassSelected: (Boolean) -> Unit
) : RecyclerView.Adapter<ClassAdapter.ViewHolder>() {

    companion object {
        const val CLASS_SELECTED = 0
    }

    interface ClassListener {
        fun onClassClicked(clazz: Class)
    }

    var selectedClass: Class? = null

    class ViewHolder(val bind: RecyclerClassBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerClassBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = classes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clazz = classes[position]

        holder.bind.clazz = clazz
        holder.bind.selected = clazz == selectedClass
        holder.bind.divider.visibility =
            if (position == classes.size - 1) GONE
            else VISIBLE

        holder.bind.root.setOnClickListener {
            val previousPos = classes.indexOf(selectedClass)
            selectedClass = clazz
            onClassSelected(true)
            notifyItemChanged(position, CLASS_SELECTED)
            notifyItemChanged(previousPos, CLASS_SELECTED)
        }

        holder.bind.buttonDetails.setOnClickListener {
            listener.onClassClicked(clazz)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(CLASS_SELECTED) -> {
                holder.bind.selected = classes[position] == selectedClass
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }
}