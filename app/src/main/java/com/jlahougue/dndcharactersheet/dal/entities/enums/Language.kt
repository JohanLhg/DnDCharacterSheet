package com.jlahougue.dndcharactersheet.dal.entities.enums

enum class Language {
    EN, FR;

    val code = name.lowercase()

    companion object {
        fun from(findValue: String) = values().find { it.name.equals(findValue, true) } ?: EN
    }
}