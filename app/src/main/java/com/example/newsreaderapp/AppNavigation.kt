package com.example.newsreaderapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreaderapp.data.network.RetrofitInstance
import com.example.newsreaderapp.data.repository.NewsRepository
import com.example.newsreaderapp.ui.screen.ArticleDetailScreen
import com.example.newsreaderapp.ui.screen.NewsListScreen
import com.example.newsreaderapp.viewmodel.NewsViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import com.example.newsreaderapp.data.model.Article
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object NewsList : Screen("news_list")
    object ArticleDetail : Screen("article_detail/{articleJson}") {
        fun createRoute(articleJson: String) = "article_detail/$articleJson"
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val repository = remember { NewsRepository(RetrofitInstance.api) }
    val viewModel = remember { NewsViewModel(repository) }
    val gson = remember { Gson() }

    NavHost(
        navController = navController,
        startDestination = Screen.NewsList.route
    ) {
        composable(Screen.NewsList.route) {
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    val json = gson.toJson(article)
                    val encodedJson = java.net.URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screen.ArticleDetail.createRoute(encodedJson))
                }
            )
        }
        composable(Screen.ArticleDetail.route) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("articleJson")
            val json = java.net.URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.toString())
            val article = gson.fromJson(json, Article::class.java)
            
            ArticleDetailScreen(
                article = article,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
