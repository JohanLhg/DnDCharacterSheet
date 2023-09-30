package com.jlahougue.dndcharactersheet.dal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves
import com.jlahougue.dndcharactersheet.dal.entities.Equipment
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.entities.Money
import com.jlahougue.dndcharactersheet.dal.entities.Notes
import com.jlahougue.dndcharactersheet.dal.entities.Preferences
import com.jlahougue.dndcharactersheet.dal.entities.Preferences.Companion.TABLE_PREFERENCES
import com.jlahougue.dndcharactersheet.dal.entities.Quests
import com.jlahougue.dndcharactersheet.dal.entities.Skill
import com.jlahougue.dndcharactersheet.dal.entities.Stats
import com.jlahougue.dndcharactersheet.dal.room.dao.AbilityDao
import com.jlahougue.dndcharactersheet.dal.room.dao.CharacterDao
import com.jlahougue.dndcharactersheet.dal.room.dao.DeathSavesDao
import com.jlahougue.dndcharactersheet.dal.room.dao.EquipmentDao
import com.jlahougue.dndcharactersheet.dal.room.dao.HealthDao
import com.jlahougue.dndcharactersheet.dal.room.dao.MoneyDao
import com.jlahougue.dndcharactersheet.dal.room.dao.NotesDao
import com.jlahougue.dndcharactersheet.dal.room.dao.PreferencesDao
import com.jlahougue.dndcharactersheet.dal.room.dao.QuestsDao
import com.jlahougue.dndcharactersheet.dal.room.dao.SkillDao
import com.jlahougue.dndcharactersheet.dal.room.dao.StatsDao

@Database(
    entities = [Ability::class, Character::class, DeathSaves::class, Equipment::class, Health::class,
        Money::class, Notes::class, Preferences::class, Quests::class, Skill::class, Stats::class],
    version = 2
)
abstract class DnDDatabase : RoomDatabase() {
    companion object {
        //Table name
        private const val DATABASE_NAME = "dnd_database"

        /********* TABLES *********
         * Character
         * Ability
         * Skill
         * Stat
         * Health
         * Death Saves
         * Equipment
         * Money
         * Notes
         * Preferences
         * Quests
         * Skill
         * Stat
         */

        //region Constants
        const val MELEE = "MELEE"
        const val RANGED = "RANGED"
        const val SPELL = "SPELL"

        const val COPPER_PIECES = "CP"
        const val SILVER_PIECES = "SP"
        const val ELECTRUM_PIECES = "EP"
        const val GOLD_PIECES = "GP"
        const val PLATINUM_PIECES = "PP"
        const val OTHER_CURRENCIES = "OTHER"
        //endregion

        private var INSTANCE: DnDDatabase? = null

        private val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO $TABLE_PREFERENCES VALUES (1, 'en')")
            }
        }

        fun getInstance(context: Context): DnDDatabase {
            if (INSTANCE == null) {
                synchronized(DnDDatabase::class.java) {
                    if (INSTANCE == null) INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): DnDDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                DnDDatabase::class.java,
                DATABASE_NAME
            ).addCallback(callback)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    //region DAOs
    abstract fun abilityDao(): AbilityDao
    abstract fun characterDao(): CharacterDao
    abstract fun deathSavesDao(): DeathSavesDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun healthDao(): HealthDao
    abstract fun moneyDao(): MoneyDao
    abstract fun notesDao(): NotesDao
    abstract fun preferencesDao(): PreferencesDao
    abstract fun questsDao(): QuestsDao
    abstract fun skillDao(): SkillDao
    abstract fun statDao(): StatsDao
    //endregion
}