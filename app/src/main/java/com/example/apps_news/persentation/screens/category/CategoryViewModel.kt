package com.example.apps_news.persentation.screens.category

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apps_news.data.model.Category
import com.example.apps_news.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.launch



class CategoryViewModel(
    private val getCategories: GetCategoriesUseCase
):ViewModel() {
    var categories by mutableStateOf<List<Category>>(emptyList())
        private set

    init {
        loadCategory()
    }

    private fun loadCategory() {
        viewModelScope.launch {
            categories = getCategories.invoke()

            Log.d("CategoryViewModel", "Categories loaded: ${categories.size} items")
        }
    }
}