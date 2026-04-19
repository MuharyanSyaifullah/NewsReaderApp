package com.example.newsreaderapp.data.repository

import com.example.newsreaderapp.data.model.Article
import com.example.newsreaderapp.data.network.NewsApiService

class NewsRepository(
    private val apiService: NewsApiService
) {
    var shouldSimulateError = false

    suspend fun getArticles(): Result<List<Article>> {
        return try {
            kotlinx.coroutines.delay(2000) // Delay to show loading/refreshing state
            if (shouldSimulateError) {
                throw Exception("Simulated network error")
            }
            val response = apiService.getNews()
            Result.success(
                response.articles.filter { !it.title.isNullOrBlank() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}