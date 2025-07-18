package com.polina.data.db.mapper

import com.polina.data.db.CategoryEntity
import com.polina.model.dto.account.Category
import com.polina.ui.models.CategoryModel


fun List<CategoryEntity>.toCategoryModel(): List<CategoryModel> {

    return this.map {
        CategoryModel(
            id = it.id,
            name = it.name,
            emoji = it.emoji,
            isIncome = it.isIncome
        )
    }
}

fun List<Category>.toCategoryEntity(): List<CategoryEntity> {
    return this.map {
        CategoryEntity(
            id = it.id,
            name = it.name,
            emoji = it.emoji,
            isIncome = it.isIncome
        )
    }
}
