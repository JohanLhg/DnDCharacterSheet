package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.databinding.RecyclerClassFilterBinding

class ClassFilterAdapter(private val listener: ClassFilterListener) : RecyclerView.Adapter<ClassFilterAdapter.ViewHolder>() {

    companion object {
        const val CHECK_CHANGED = 0
    }

    interface ClassFilterListener {
        fun onFilterChange(filteredClasses: List<String>)
    }

    class ViewHolder(val bind: RecyclerClassFilterBinding) : RecyclerView.ViewHolder(bind.root) {
        init { this.setIsRecyclable(false) }
    }

    var classes = listOf<String>()
        set(value) {
            field = value
            activeClasses.clear()
            notifyDataSetChanged()
        }
    private val activeClasses = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerClassFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = classes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clazz = classes[position]

        holder.bind.charClass = clazz
        holder.bind.active = activeClasses.contains(clazz)

        holder.bind.layoutClassFilter.setOnClickListener {
            if (activeClasses.contains(clazz)) {
                activeClasses.remove(clazz)
                holder.bind.active = false
            } else {
                activeClasses.add(clazz)
                holder.bind.active = true
            }
            listener.onFilterChange(activeClasses)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.contains(CHECK_CHANGED) -> {
                val clazz = classes[position]
                holder.bind.active = activeClasses.contains(clazz)
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun uncheckAll() {
        activeClasses.clear()
        notifyItemRangeChanged(0, classes.size, CHECK_CHANGED)
        listener.onFilterChange(activeClasses)
    }
}