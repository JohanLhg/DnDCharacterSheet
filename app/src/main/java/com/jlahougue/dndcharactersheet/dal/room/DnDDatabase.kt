package com.jlahougue.dndcharactersheet.dal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jlahougue.dndcharactersheet.dal.entities.Ability
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.entities.CharacterSpell
import com.jlahougue.dndcharactersheet.dal.entities.CharacterWeapon
import com.jlahougue.dndcharactersheet.dal.entities.Class
import com.jlahougue.dndcharactersheet.dal.entities.ClassLevel
import com.jlahougue.dndcharactersheet.dal.entities.ClassSpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.DamageType
import com.jlahougue.dndcharactersheet.dal.entities.DeathSaves
import com.jlahougue.dndcharactersheet.dal.entities.Equipment
import com.jlahougue.dndcharactersheet.dal.entities.Health
import com.jlahougue.dndcharactersheet.dal.entities.Money
import com.jlahougue.dndcharactersheet.dal.entities.Notes
import com.jlahougue.dndcharactersheet.dal.entities.Preferences
import com.jlahougue.dndcharactersheet.dal.entities.Preferences.Companion.TABLE_PREFERENCES
import com.jlahougue.dndcharactersheet.dal.entities.Property
import com.jlahougue.dndcharactersheet.dal.entities.Quests
import com.jlahougue.dndcharactersheet.dal.entities.Skill
import com.jlahougue.dndcharactersheet.dal.entities.Spell
import com.jlahougue.dndcharactersheet.dal.entities.SpellClass
import com.jlahougue.dndcharactersheet.dal.entities.SpellDamage
import com.jlahougue.dndcharactersheet.dal.entities.SpellSlot
import com.jlahougue.dndcharactersheet.dal.entities.Stats
import com.jlahougue.dndcharactersheet.dal.entities.Weapon
import com.jlahougue.dndcharactersheet.dal.entities.WeaponProperty
import com.jlahougue.dndcharactersheet.dal.entities.enums.Language
import com.jlahougue.dndcharactersheet.dal.entities.enums.UnitSystem
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityModifierView
import com.jlahougue.dndcharactersheet.dal.entities.views.AbilityView
import com.jlahougue.dndcharactersheet.dal.entities.views.CharacterSpellStatsView
import com.jlahougue.dndcharactersheet.dal.entities.views.ProficiencyView
import com.jlahougue.dndcharactersheet.dal.entities.views.SkillView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellSlotView
import com.jlahougue.dndcharactersheet.dal.entities.views.SpellcastingView
import com.jlahougue.dndcharactersheet.dal.entities.views.WeaponView
import com.jlahougue.dndcharactersheet.dal.room.converters.AbilityNameTypeConverter
import com.jlahougue.dndcharactersheet.dal.room.converters.LanguageTypeConverter
import com.jlahougue.dndcharactersheet.dal.room.converters.SkillNameTypeConverter
import com.jlahougue.dndcharactersheet.dal.room.converters.UnitSystemTypeConverter
import com.jlahougue.dndcharactersheet.dal.room.dao.AbilityDao
import com.jlahougue.dndcharactersheet.dal.room.dao.CharacterDao
import com.jlahougue.dndcharactersheet.dal.room.dao.CharacterSpellDao
import com.jlahougue.dndcharactersheet.dal.room.dao.CharacterWeaponDao
import com.jlahougue.dndcharactersheet.dal.room.dao.ClassDao
import com.jlahougue.dndcharactersheet.dal.room.dao.ClassLevelDao
import com.jlahougue.dndcharactersheet.dal.room.dao.ClassSpellSlotDao
import com.jlahougue.dndcharactersheet.dal.room.dao.DamageTypeDao
import com.jlahougue.dndcharactersheet.dal.room.dao.DeathSavesDao
import com.jlahougue.dndcharactersheet.dal.room.dao.EquipmentDao
import com.jlahougue.dndcharactersheet.dal.room.dao.HealthDao
import com.jlahougue.dndcharactersheet.dal.room.dao.MoneyDao
import com.jlahougue.dndcharactersheet.dal.room.dao.NotesDao
import com.jlahougue.dndcharactersheet.dal.room.dao.PreferencesDao
import com.jlahougue.dndcharactersheet.dal.room.dao.PropertyDao
import com.jlahougue.dndcharactersheet.dal.room.dao.QuestsDao
import com.jlahougue.dndcharactersheet.dal.room.dao.SkillDao
import com.jlahougue.dndcharactersheet.dal.room.dao.SpellClassDao
import com.jlahougue.dndcharactersheet.dal.room.dao.SpellDamageDao
import com.jlahougue.dndcharactersheet.dal.room.dao.SpellDao
import com.jlahougue.dndcharactersheet.dal.room.dao.SpellSlotDao
import com.jlahougue.dndcharactersheet.dal.room.dao.SpellcastingDao
import com.jlahougue.dndcharactersheet.dal.room.dao.StatsDao
import com.jlahougue.dndcharactersheet.dal.room.dao.WeaponDao
import com.jlahougue.dndcharactersheet.dal.room.dao.WeaponPropertyDao

@Database(
    entities = [Ability::class, Character::class, CharacterSpell::class, CharacterWeapon::class,
        Class::class, ClassLevel::class, ClassSpellSlot::class, DamageType::class,
        DeathSaves::class, Equipment::class, Health::class, Money::class, Notes::class,
        Preferences::class, Property::class, Quests::class, Skill::class, Spell::class,
        SpellClass::class, SpellDamage::class, SpellSlot::class, Stats::class, Weapon::class,
        WeaponProperty::class],
    views = [AbilityView::class, AbilityModifierView::class, CharacterSpellStatsView::class,
        ProficiencyView::class, SkillView::class, SpellcastingView::class, SpellSlotView::class,
        WeaponView::class],
    version = 62
)
@TypeConverters(
    value = [AbilityNameTypeConverter::class, SkillNameTypeConverter::class,
        LanguageTypeConverter::class, UnitSystemTypeConverter::class]
)
abstract class DnDDatabase : RoomDatabase() {
    companion object {
        //Database name
        private const val DATABASE_NAME = "dnd_database"

        private var INSTANCE: DnDDatabase? = null

        private val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO $TABLE_PREFERENCES VALUES (1, '${Language.EN.code}', '${UnitSystem.METRIC}')")
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
    abstract fun characterSpellDao(): CharacterSpellDao
    abstract fun characterWeaponDao(): CharacterWeaponDao
    abstract fun classDao(): ClassDao
    abstract fun classLevelDao(): ClassLevelDao
    abstract fun classSpellSlotDao(): ClassSpellSlotDao
    abstract fun damageTypeDao(): DamageTypeDao
    abstract fun deathSavesDao(): DeathSavesDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun healthDao(): HealthDao
    abstract fun moneyDao(): MoneyDao
    abstract fun notesDao(): NotesDao
    abstract fun preferencesDao(): PreferencesDao
    abstract fun propertyDao(): PropertyDao
    abstract fun questsDao(): QuestsDao
    abstract fun skillDao(): SkillDao
    abstract fun spellcastingDao(): SpellcastingDao
    abstract fun spellClassDao(): SpellClassDao
    abstract fun spellDamageDao(): SpellDamageDao
    abstract fun spellDao(): SpellDao
    abstract fun spellSlotDao(): SpellSlotDao
    abstract fun statDao(): StatsDao
    abstract fun weaponDao(): WeaponDao
    abstract fun weaponPropertyDao(): WeaponPropertyDao
    //endregion
}