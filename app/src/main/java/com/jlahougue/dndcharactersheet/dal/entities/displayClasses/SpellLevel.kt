package com.jlahougue.dndcharactersheet.dal.entities.displayClasses

import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView

class SpellLevel(
    val spellSlot: SpellSlotView,
    var spells: List<SpellWithCharacterInfo> = listOf()
) {
    fun filterSpells(search: String = "", classFilter: List<String> = listOf()) {
        if (search.isEmpty() && classFilter.isEmpty()) return
        spells = spells.filter { spell ->
            (search.isEmpty() || spell.name.contains(search, true)) &&
                    (classFilter.isEmpty() || spell.classes.any { clazz -> classFilter.contains(clazz.name) })
        }
    }

    fun copy(
        spellSlot: SpellSlotView = this.spellSlot.copy(),
        spells: List<SpellWithCharacterInfo> = this.spells.map { it.copy() }
    ) = SpellLevel(spellSlot, spells)

    override fun toString(): String {
        return "\n$spellSlot\n$spells"
    }
}