package com.polina.fintrackr.core.data.repositories

import com.polina.fintrackr.core.data.network.CategoryApiService
import com.polina.fintrackr.core.domain.repositories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApiService
) : CategoryRepository {
    override suspend fun getCategories() = api.getCategories()

    override suspend fun getCategoryType(isIncome: Boolean) = api.getCategoryType(isIncome)
}