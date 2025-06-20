package com.polina.fintrackr.features.articles.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.model.account.Category
import com.polina.fintrackr.core.data.mapper.toCategoryModel
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.repositories.AccountRepository
import com.polina.fintrackr.core.data.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _categories = mutableStateOf<List<CategoryModel>>(emptyList())
    val categories: State<List<CategoryModel>> = _categories
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun getCategories() {
        viewModelScope.launch {
            _error.value = null
            try {
                val response = withContext(Dispatchers.IO) {
                    categoryRepository.getCategories()
                }
                if (response.isSuccessful) {
                    _categories.value = response.body()?.toCategoryModel() ?: emptyList()
                    _error.value = null
                } else {
                    throw NetworkException()
                }
            } catch (e: NetworkException) {
                _error.value = "Ошибка при выходе в сеть, проверьте соединение}"
            } catch (e: Exception) {
                _error.value = "Ошибка при выходе в сеть, проверьте соединение"
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