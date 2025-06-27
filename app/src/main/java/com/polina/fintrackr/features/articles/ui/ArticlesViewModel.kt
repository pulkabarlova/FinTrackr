package com.polina.fintrackr.features.articles.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.mapper.toCategoryModel
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.domain.use_case.GetCategoriesUseCase
import com.polina.fintrackr.core.domain.repositories.CategoryRepository
import com.polina.fintrackr.features.articles.domain.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
/**
 * Управляет состоянием и логикой отображения экрана статьей пользователя.
 */
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories = mutableStateOf<List<CategoryModel>>(emptyList())
    val categories: State<List<CategoryModel>> = _categories

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun getCategories() {
        viewModelScope.launch {
            _error.value = null
            try {
                val result = getCategoriesUseCase()
                _categories.value = result
            } catch (e: NetworkException) {
                _error.value = "Ошибка загрузки данных"
            }
        }
    }

    init {
        getCategories()
    }

    fun clearError() {
        _error.value = null
    }
}