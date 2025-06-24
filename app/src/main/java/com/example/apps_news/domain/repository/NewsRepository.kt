package com.example.apps_news.domain.repository

import com.example.apps_news.data.model.Article
import com.example.apps_news.data.model.Category
import com.example.apps_news.data.model.Source

interface NewsRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getSources(category: Category?, page: Int, query: String?): Result<List<Source>>
    suspend fun getArticles(sourceId: String?, page: Int, query: String?): Result<List<Article>>
}