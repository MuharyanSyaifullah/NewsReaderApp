package com.example.newsreaderapp.data.repository

import com.example.newsreaderapp.data.model.Article
import com.example.newsreaderapp.data.network.NewsApiService

class NewsRepository(
    private val apiService: NewsApiService
) {
    suspend fun getArticles(): Result<List<Article>> {
        return try {
            val response = apiService.getNews()
            Result.success(
                response.articles.filter { !it.title.isNullOrBlank() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}