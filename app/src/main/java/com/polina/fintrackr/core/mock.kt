package com.polina.fintrackr.core

import com.polina.fintrackr.core.domain.model.category.Category
import com.polina.fintrackr.core.domain.model.transaction.Transaction

fun generateMockData():List<Transaction> {
    val categories = listOf(
        Category(1, "Зарплата", "💰", true),
        Category(2, "Вкусняшки", "\uD83C\uDF6C", false),
        Category(3, "Аренда", "\uD83C\uDFE0", false),
        Category(4, "Фриланс", "💻", true),
        Category(5, name = "Аренда сарая", emoji = "АС", false),
        Category(6, name = "Любимка", emoji = "\uD83E\uDE77", false),
        Category(7, name = "Продукты", emoji = "\uD83C\uDF6C", false),
    )

    val transactions = listOf(
        Transaction(
            id = 1,
            account = "",
            category = categories[0],
            amount = 100000,
            transactionDate = "",
            comment = "Зарплата за июнь",
            createdAt = "",
            updatedAt = ""
        ),
        Transaction(
            id = 2,
            account = "",
            category = categories[1],
            amount = 500,
            transactionDate = "",
            comment = "Вкусняшки",
            createdAt = "",
            updatedAt = ""
    ),
        Transaction(
            id = 3,
            account = "",
            category = categories[2],
            amount = 10000,
            transactionDate = "",
            comment = "Аренда",
            createdAt = "",
            updatedAt = ""
        ),
    )
    return transactions
}