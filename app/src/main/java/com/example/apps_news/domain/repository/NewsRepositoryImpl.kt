package com.example.apps_news.domain.repository

import com.example.apps_news.data.api.NewsApiService
import com.example.apps_news.data.model.Article
import com.example.apps_news.data.model.Category
import com.example.apps_news.data.model.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImpl (
    private val apiService: NewsApiService
): NewsRepository {
    private val apiKey = "241790a497ae408798a2198278d94bef"

    override suspend fun getCategories(): List<Category> {
        return Category.entries
    }

    override suspend fun getSources(category: Category?, page: Int, query: String?): Result<List<Source>> {
        return withContext(Dispatchers.Main) {
            try {
                val response = apiService.getSources(category?.value, apiKey, page, 20, query)

                if (response.sources.isNotEmpty()) {
                    Result.success(response.sources)
                } else {
                    Result.failure(Exception("API error: ${response.status}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getArticles(sourceId: String?, page: Int, query: String?): Result<List<Article>> {
        return withContext(Dispatchers.Main) {
            try {
                val response = apiService.getArticles(sourceId, apiKey, page, 20, query)

                if (response.articles.isNotEmpty()) {
                    Result.success(response.articles)
                } else {
                    Result.failure(Exception("API error: ${response.status}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}