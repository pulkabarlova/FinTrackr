package com.polina.fintrackr.core.data.mapper

enum class Currency(val symbol: String) {
    RUB("₽"),
    USD("$"),
    EUR("€");

    companion object {
        fun fromString(value: String): Currency? {
            return values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}