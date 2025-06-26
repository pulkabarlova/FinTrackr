package com.polina.fintrackr.core.data.use_case

import com.polina.fintrackr.core.data.dto.model.category.Category
import com.polina.fintrackr.core.data.mapper.toCategoryModel
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.domain.repositories.CategoryRepository
import com.polina.fintrackr.features.articles.domain.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<CategoryModel>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                repository.getCategories()
            }
            if (response.isSuccessful) {
                val data = response.body()?.toCategoryModel() ?: emptyList()
                Result.success(data)
            } else {
                Result.failure(NetworkException())
            }
        } catch (e: Exception) {
            Result.failure(NetworkException())
        }
    }
}