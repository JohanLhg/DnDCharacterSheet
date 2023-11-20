package com.jlahougue.dndcharactersheet.dal.entities.enums

enum class UnitSystem {
    IMPERIAL, METRIC;

    companion object {
        fun from(findValue: String) = values().find { it.name.equals(findValue, true) } ?: METRIC
    }
}