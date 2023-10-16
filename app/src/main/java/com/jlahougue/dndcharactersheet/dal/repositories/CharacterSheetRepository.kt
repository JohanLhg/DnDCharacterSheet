package com.jlahougue.dndcharactersheet.dal.repositories

import android.app.Application
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.entities.Equipment
import com.jlahougue.dndcharactersheet.dal.entities.Notes
import com.jlahougue.dndcharactersheet.dal.entities.Quests
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.Spellcasting
import com.jlahougue.dndcharactersheet.dal.firebase.dao.CharacterSheetDao
import com.jlahougue.dndcharactersheet.dal.firebase.documentLayouts.CharacterSheet

class CharacterSheetRepository(application: Application) {
    private val firebaseDao = CharacterSheetDao()
    private val characterRepository = CharacterRepository(application)
    private val abilityRepository = AbilityRepository(application)
    private val skillRepository = SkillRepository(application)
    private val statsRepository = StatsRepository(application)
    private val spellcastingRepository = SpellcastingRepository(application)
    private val spellSlotRepository = SpellSlotRepository(application)
    private val characterSpellRepository = CharacterSpellRepository(application)
    private val characterWeaponRepository = CharacterWeaponRepository(application)
    private val healthRepository = HealthRepository(application)
    private val deathSavesRepository = DeathSavesRepository(application)
    private val notesRepository = NotesRepository(application)
    private val questsRepository = QuestsRepository(application)
    private val moneyRepository = MoneyRepository(application)
    private val equipmentRepository = EquipmentRepository(application)

    fun create(): Long {
        val characterID = characterRepository.insert(Character())
        abilityRepository.create(characterID)
        skillRepository.create(characterID)
        statsRepository.create(characterID)
        healthRepository.create(characterID)
        deathSavesRepository.create(characterID)
        spellcastingRepository.create(characterID)
        spellSlotRepository.create(characterID)
        notesRepository.create(characterID)
        questsRepository.create(characterID)
        moneyRepository.create(characterID)
        equipmentRepository.create(characterID)

        saveToRemote(characterID)

        return characterID
    }

    private fun saveToLocal(characterID: Long, characterSheet: CharacterSheet) {
        characterSheet.character?.let { characterRepository.saveToLocal(it) }
        characterSheet.abilities.forEach { (_, ability) -> abilityRepository.saveToLocal(ability) }
        characterSheet.skills.forEach { (_, skill) -> skillRepository.saveToLocal(skill) }
        characterSheet.stats?.let { statsRepository.saveToLocal(it) }
        characterSheet.health?.let { healthRepository.saveToLocal(it) }
        characterSheet.deathSaves?.let { deathSavesRepository.saveToLocal(it) }
        characterSheet.spellcastingAbility?.let {
            spellcastingRepository.saveToLocal(Spellcasting(characterID, it))
        }
        characterSheet.spellSlots.forEach { (level, count) ->
            spellSlotRepository.saveToLocal(SpellSlot(characterID, level, count))
        }
        characterSheet.spells.forEach { (_, spell) -> characterSpellRepository.saveToLocal(spell) }
        characterSheet.weapons.forEach { (id, count) ->
            characterWeaponRepository.saveToLocal(CharacterWeapon(characterID, id, count))
        }
        characterSheet.notes?.let { notesRepository.saveToLocal(Notes(characterID, it)) }
        characterSheet.quests?.let { questsRepository.saveToLocal(Quests(characterID, it)) }
        characterSheet.money?.let { moneyRepository.saveToLocal(it) }
        characterSheet.equipment?.let { equipmentRepository.saveToLocal(Equipment(characterID, it)) }
    }

    private fun saveToRemote(characterID: Long) {
        val characterSheet = CharacterSheet(
            characterRepository.get(characterID),
            abilityRepository.getMap(characterID),
            skillRepository.getMap(characterID),
            statsRepository.get(characterID),
            healthRepository.get(characterID),
            deathSavesRepository.get(characterID),
            spellcastingRepository.getAbility(characterID),
            spellSlotRepository.getMap(characterID),
            characterSpellRepository.getMap(characterID),
            characterWeaponRepository.getMap(characterID),
            notesRepository.get(characterID),
            questsRepository.get(characterID),
            moneyRepository.get(characterID),
            equipmentRepository.get(characterID)
        )

        firebaseDao.insert(characterSheet)
    }

    fun createCharacterIfNotExists(callback: () -> Unit) {
        firebaseDao.loadCharacterSheets(::saveToLocal) {
            val characterIDs = characterRepository.getIDs()
            characterIDs.forEach { characterID ->
                if (!it.contains(characterID))
                    saveToRemote(characterID)
            }

            if (!characterRepository.exists()) create()

            callback()
        }
    }
}