package com.example.newsreaderapp.ui.state

import com.example.newsreaderapp.data.model.Article

sealed class NewsUiState {
    data object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}