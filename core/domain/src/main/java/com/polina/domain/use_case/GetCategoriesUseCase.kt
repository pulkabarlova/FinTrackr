package com.polina.domain.use_case


import com.polina.domain.repositories.CategoryRepository
import com.polina.ui.models.CategoryModel
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
