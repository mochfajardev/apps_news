package com.example.apps_news.data.api

import com.example.apps_news.data.model.Article
import com.example.apps_news.data.model.Source
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/sources")
    suspend fun getSources(
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("q") q: String? = null
    ): SourceResponse

    @GET("v2/everything")
    suspend fun getArticles(
        @Query("sources") sources: String?, // comma separated id
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("q") q: String? = null
    ): ArticleResponse
}

data class SourceResponse(val status: String, val sources: List<Source>)
data class ArticleResponse(val status: String, val articles: List<Article>)