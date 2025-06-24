package com.example.apps_news.data.model

data class Article(
    val source: SourceSimple,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class SourceSimple(
    val id: String?,
    val name: String
)