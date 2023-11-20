package com.jlahougue.dndcharactersheet.dal.firebase.documentLayouts

import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.entities.Money
import com.jlahougue.dndcharactersheet.dal.entities.Skill
import com.jlahougue.dndcharactersheet.dal.entities.Stats

class CharacterSheet(
    var character: Character? = null,
    var abilities: Map<String, Ability> = mapOf(),
    var skills: Map<String, Skill> = mapOf(),
    var stats: Stats? = null,
    var health: Health? = null,
    var deathSaves: DeathSaves? = null,
    var spellSlots: Map<String, Int> = mapOf(),
    var spells: Map<String, CharacterSpell> = mapOf(),
    var weapons: Map<String, CharacterWeapon> = mapOf(),
    var notes: String? = null,
    var quests: String? = null,
    var money: Money? = null,
    var equipment: String? = null
) {
        val id: Long
            get() = character?.id ?: 0

        override fun toString(): String {
            return """
                CharacterSheet:
                Character:
                $character
                Abilities:
                ${abilities.forEach { it.toString() }}
                Skills:
                ${skills.forEach { it.toString() }}
                Stats:
                $stats
                Health:
                $health
                Death Saves:
                $deathSaves
                Spell Slots:
                ${spellSlots.forEach { it.toString() }}
                Spells:
                ${spells.forEach { it.toString() }}
                Weapons:
                ${weapons.forEach { it.toString() }}
                Notes:
                $notes
                Quests:
                $quests
                Money:
                $money
                Equipment:
                $equipment
            """.trimIndent()
        }
}