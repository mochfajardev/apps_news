package com.example.apps_news.domain.usecase

import com.example.apps_news.data.model.Category
import com.example.apps_news.data.model.Source
import com.example.apps_news.domain.repository.NewsRepository

class GetSourcesUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun  invoke(category: Category?, page: Int, query: String?) : Result<List<Source>> {
        return repository.getSources(category, page, query)
    }
}