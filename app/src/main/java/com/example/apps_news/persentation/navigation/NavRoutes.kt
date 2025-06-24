package com.example.apps_news.persentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apps_news.data.model.Category
import com.example.apps_news.persentation.screens.article.ArticleDetailScreen
import com.example.apps_news.persentation.screens.article.ArticleScreen
import com.example.apps_news.persentation.screens.category.CategoryScreen
import com.example.apps_news.persentation.screens.source.SourceScreen
import java.net.URLDecoder

object NavRoutes {
    const val CATEGORY_LIST = "category_list"
    const val SOURCE_LIST = "source_list"
    const val ARTICLE_LIST = "article_list"
    const val ARTICLE_DETAIL = "article_detail"
}

@Composable
fun NewsNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.CATEGORY_LIST) {
        composable(NavRoutes.CATEGORY_LIST) {
            CategoryScreen(navController = navController)
        }
        composable("${NavRoutes.SOURCE_LIST}/{category}") { backStackEntry ->
            val categoryStr = backStackEntry.arguments?.getString("category") ?: ""
            val category = Category.entries.find { it.value == categoryStr }
            if (category != null) {
                SourceScreen(category = category, navController = navController)
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: Kategori tidak ditemukan.")
                }
            }
        }
        composable("${NavRoutes.ARTICLE_LIST}/{sourceId}") { backStackEntry ->
            val sourceId = backStackEntry.arguments?.getString("sourceId") ?: ""
            ArticleScreen(sourceId = sourceId, navController = navController)
        }
        composable(
            route = "${NavRoutes.ARTICLE_DETAIL}?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            ArticleDetailScreen(url = URLDecoder.decode(url, "UTF-8"))
        }
    }
}