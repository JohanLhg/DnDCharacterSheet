package com.jlahougue.dndcharactersheet.dal.entities.displayClasses

import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView

class SpellLevel(
    val spellSlot: SpellSlotView,
    var spells: List<SpellWithCharacterInfo> = listOf()
) {
    fun filterSpells(search: String) {
        for (i in spells.indices)
            if (!spells[i].name.contains(search, true)) (spells as ArrayList).removeAt(i)
    }

    fun copy(): SpellLevel {
        val copy = SpellLevel(spellSlot)
        copy.spells = ArrayList(spells)
        return copy
    }

    override fun toString(): String {
        return "\n$spellSlot\n$spells"
    }
}