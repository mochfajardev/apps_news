package com.example.apps_news.domain.usecase

import com.example.apps_news.data.model.Article
import com.example.apps_news.domain.repository.NewsRepository

class GetArticlesUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(sourceId: String?, page: Int, query: String?) : Result<List<Article>> {
        return repository.getArticles(sourceId, page, query)
    }
}