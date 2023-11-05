package com.jlahougue.dndcharactersheet.dal.entities.displayClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.dal.entities.Class

@Entity(tableName = Character.TABLE_CHARACTER)
class CharacterInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Character.CHARACTER_ID)
    var id: Long = 0,
    @ColumnInfo(name = Character.CHARACTER_NAME)
    var name: String = "",
    @ColumnInfo(name = Character.CHARACTER_RACE)
    var race: String = "",
    @ColumnInfo(name = Character.CHARACTER_CLASS)
    var clazz: String = "",
    @Relation(
        parentColumn = Character.CHARACTER_CLASS,
        entityColumn = Class.CLASS_NAME,
        entity = Class::class
    )
    var classInfo: Class? = null,
    @ColumnInfo(name = Character.CHARACTER_LEVEL)
    var level: Int = 1,
    @ColumnInfo(name = Character.CHARACTER_GENDER)
    var gender: String = "",
    @ColumnInfo(name = Character.CHARACTER_AGE)
    var age: Int = 0,
    @ColumnInfo(name = Character.CHARACTER_HEIGHT)
    var height: Double = 0.0,
    @ColumnInfo(name = Character.CHARACTER_WEIGHT)
    var weight: Int = 0,
    @ColumnInfo(name = Character.CHARACTER_PERSONALITY)
    var personality: String = "",
    @ColumnInfo(name = Character.CHARACTER_IDEALS)
    var ideals: String = "",
    @ColumnInfo(name = Character.CHARACTER_BONDS)
    var bonds: String = "",
    @ColumnInfo(name = Character.CHARACTER_FLAWS)
    var flaws: String = "",
    @ColumnInfo(name = Character.CHARACTER_BACKGROUND_TITLE)
    var backgroundTitle: String = "",
    @ColumnInfo(name = Character.CHARACTER_BACKGROUND)
    var background: String = "",
    @ColumnInfo(name = Character.CHARACTER_IS_FAVORITE)
    var isFavorite: Boolean = false
) {
    fun setClass(clazz: Class) {
        this.clazz = clazz.name
        this.classInfo = clazz
    }

    fun isSpellcaster() = classInfo?.isSpellcaster() ?: false

    fun getCharacter() = Character(
        id,
        name,
        race,
        clazz,
        level,
        gender,
        age,
        height,
        weight,
        personality,
        ideals,
        bonds,
        flaws,
        backgroundTitle,
        background,
        isFavorite
    )

    override fun toString(): String {
        return """
            $id
            $name
            $race
            $clazz
            $level
            $gender
            $age
            $height
            $weight
            $personality
            $ideals
            $bonds
            $flaws
            $backgroundTitle
            $background
            $isFavorite
        """.trimIndent()
    }
}