package com.example.apps_news.domain.usecase

import com.example.apps_news.data.model.Category
import com.example.apps_news.domain.repository.NewsRepository

class GetCategoriesUseCase (
    private val repository: NewsRepository
) {
    suspend operator fun invoke():List<Category> {
        return repository.getCategories()
    }
}