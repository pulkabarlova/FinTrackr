package com.polina.fintrackr.core.data.mapper

import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.dto.model.account.Category
import com.polina.fintrackr.features.articles.domain.CategoryModel
import com.polina.fintrackr.features.count.domain.AccountModel
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