package com.example.newsreaderapp.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val title: String? = null,
    val description: String? = null,
    val urlToImage: String? = null,
    val content: String? = null,
    val author: String? = null,
    val publishedAt: String? = null,
    val url: String? = null
)