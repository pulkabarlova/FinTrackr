package com.polina.fintrackr.core

import com.polina.fintrackr.core.domain.Category
import com.polina.fintrackr.core.domain.Transaction

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
            comment = "Зарплата за июнь"
        ),
        Transaction(
            id = 2,
            account = "",
            category = categories[1],
            amount = 5000,
            transactionDate = ""
        ),
        Transaction(
            id = 3,
            account = "",
            category = categories[2],
            amount = 25000,
            transactionDate = ""
        ),
        Transaction(
            id = 4,
            account = "",
            category = categories[3],
            amount = 15000
        ),
        Transaction(
            id = 5,
            account = "",
            category = categories[4],
            amount = 15000
        ),
        Transaction(
            id = 6,
            account = "",
            category = categories[5],
            amount = 100000
        ),
        Transaction(
            id = 7,
            account = "",
            category = categories[6],
            amount = 6000
        ),
    )
    return transactions
}