package com.example.apps_news.persentation.screens.article

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.apps_news.data.model.Article
import com.example.apps_news.persentation.navigation.NavRoutes
import com.example.apps_news.persentation.screens.source.SourceScreen
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder

@Composable
fun ArticleScreen(sourceId: String, navController: NavController) {
    val viewModel: ArticlesViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = sourceId) {
        viewModel.loadArticles(sourceId, "")
    }

    val listState = rememberLazyListState()
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && !uiState.isLoading && !uiState.endReached) {
            viewModel.loadArticles(sourceId)
        }
    }

    ArticleContent(uiState = uiState, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleContent(
    uiState: ArticleUiState,
    navController: NavController,
) {

    if (uiState.articles.isEmpty() && uiState.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Error: ${uiState.error}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    if (uiState.articles.isEmpty() && uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Arcticle",
                        style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 24.sp,
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Column {
            Box(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.articles) { article ->
                        ArticleItem(article = article) {
                            it.url.let { url ->
                                val encodedUrl = URLEncoder.encode(url, "UTF-8")
                                navController.navigate("${NavRoutes.ARTICLE_DETAIL}?url=$encodedUrl")
                            }
                        }
                    }

                    item {
                        if (uiState.isLoading && uiState.articles.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun ArticleItem(article: Article, onItemClick: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(article) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "Sample Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(text = article.title)
            Text(text = article.description ?: "")
        }
    }
}