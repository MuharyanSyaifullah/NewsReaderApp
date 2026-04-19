package com.example.newsreaderapp.data.network

import com.example.newsreaderapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String = "technology",
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = ApiConfig.API_KEY
    ): NewsResponse
}