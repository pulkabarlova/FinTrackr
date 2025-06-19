package com.polina.fintrackr.core.data.repositories

import com.polina.fintrackr.core.data.dto.account.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.network.AccountApiService
import com.polina.fintrackr.core.data.network.CategoryApiService
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: CategoryApiService
) {
    suspend fun getCategories() = api.getCategories()

    suspend fun getCategoryType(isIncome: Boolean) = api.getCategoryType(isIncome)
}