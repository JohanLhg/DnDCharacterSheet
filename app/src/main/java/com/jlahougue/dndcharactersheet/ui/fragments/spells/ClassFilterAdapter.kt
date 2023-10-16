package com.jlahougue.dndcharactersheet.ui.fragments.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlahougue.dndcharactersheet.databinding.RecyclerClassFilterBinding

class ClassFilterAdapter(private val listener: ClassFilterListener) : RecyclerView.Adapter<ClassFilterAdapter.ViewHolder>() {

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
        val charClass = classes[position]

        holder.bind.charClass = charClass
        holder.bind.active = activeClasses.contains(charClass)

        holder.bind.layoutClassFilter.setOnClickListener {
            if (activeClasses.contains(charClass)) {
                activeClasses.remove(charClass)
                holder.bind.active = false
            } else {
                activeClasses.add(charClass)
                holder.bind.active = true
            }
            listener.onFilterChange(activeClasses)
        }
    }
}