package com.polina.articles.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.model.NetworkException
import com.polina.ui.models.CategoryModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * Управляет состоянием и логикой отображения экрана статьей пользователя.
 */

class ArticlesViewModel @Inject constructor(
    private val getCategoriesUseCase: com.polina.domain.use_case.GetCategoriesUseCase
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