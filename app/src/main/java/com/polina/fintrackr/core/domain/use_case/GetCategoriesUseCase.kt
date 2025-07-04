package com.polina.fintrackr.core.domain.use_case

import com.polina.fintrackr.core.data.dto.category.Category
import com.polina.fintrackr.core.data.mapper.toCategoryModel
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.domain.repositories.CategoryRepository
import com.polina.fintrackr.features.articles.domain.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
/**
 * Получение категорий
 */
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<CategoryModel> {
        return repository.getCategories()
    }
}
