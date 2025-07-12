package com.polina.model.dto.account

enum class Currency(val symbol: String) {
    RUB("\u20BD"),
    USD("\u0024"),
    EUR("\u20AC");

    companion object {
        fun fromCode(code: String): Currency? {
            return values().find { it.name == code }
        }
    }
}