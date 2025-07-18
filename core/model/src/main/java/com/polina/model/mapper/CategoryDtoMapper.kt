package com.polina.model.mapper

import com.polina.model.dto.account.Category
import com.polina.ui.models.CategoryModel

/**
 * Маппинг из Category (сеть) в CategoryModel(ui)
 */
fun List<Category>.toCategoryModel(): List<CategoryModel> {

    return this.map {
        CategoryModel(
            id = it.id,
            name = it.name,
            emoji = it.emoji,
            isIncome = it.isIncome
        )
    }
}
