package com.polina.data.repositories

import android.util.Log
import com.polina.data.db.CategoryDao
import com.polina.data.db.mapper.toCategoryEntity
import com.polina.data.db.mapper.toCategoryModel
import com.polina.model.mapper.toCategoryModel
import com.polina.model.NetworkException
import com.polina.data.network.api_service.CategoryApiService
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.repositories.CategoryRepository
import com.polina.ui.models.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Репозиторий для получения категорий
 */
class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApiService,
    private val categoryDao: CategoryDao,
    private val networkMonitor: NetworkMonitor
) : CategoryRepository {

    override suspend fun getCategories(): List<CategoryModel> {
        if (networkMonitor.isConnected()) {
            try {
                val response = withContext(Dispatchers.IO) { api.getCategories() }
                if (response.isSuccessful) {
                    val categories = response.body() ?: emptyList()
                    categoryDao.insertCategories(categories.toCategoryEntity())
                    return categories.toCategoryModel()
                } else {
                    throw NetworkException()
                }
            } catch (e: Exception) {
                throw NetworkException()
            }
        } else {
            return categoryDao.getAll().toCategoryModel()
        }
    }

    override suspend fun getCategoryType(isIncome: Boolean) = api.getCategoryType(isIncome)
}