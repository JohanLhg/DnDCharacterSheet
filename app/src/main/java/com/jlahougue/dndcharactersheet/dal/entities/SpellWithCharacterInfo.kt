package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell.Companion.CHARACTER_SPELL_NAME
import com.jlahougue.dndcharactersheet.dal.entities.Spell.Companion.SPELL_NAME

class SpellWithCharacterInfo(
    @Embedded
    val spell: Spell,
    @Relation(
        parentColumn = SPELL_NAME,
        entityColumn = CHARACTER_SPELL_NAME
    )
    val characterSpell: CharacterSpell? = null
)