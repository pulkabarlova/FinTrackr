package com.polina.fintrackr.features.articles.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.model.account.Category
import com.polina.fintrackr.core.data.mapper.toCategoryModel
import com.polina.fintrackr.core.data.repositories.AccountRepository
import com.polina.fintrackr.core.data.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _categories = mutableStateOf<List<CategoryModel>>(emptyList())
    val categories: State<List<CategoryModel>> = _categories
    suspend fun getCategories() {
        val response = categoryRepository.getCategories()
        if (response.isSuccessful) {
            _categories.value = response.body()?.toCategoryModel()?: emptyList()
        }
    }

    init {
        viewModelScope.launch { getCategories() }
    }
}