package com.example.apps_news.persentation.screens.source

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.apps_news.data.model.Category
import com.example.apps_news.data.model.Source
import com.example.apps_news.persentation.navigation.NavRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun SourceScreen(category: Category, navController: NavController) {
    val viewModel: SourcesViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(category) {
        viewModel.loadSource(category, null, true)
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
            viewModel.loadSource(category, reset = false)
        }
    }

    SourceContent(uiState = uiState, navController, viewModel, category)
}

@Composable
fun SourceContent(
    uiState: SourceUiState,
    navController: NavController,
    viewModel: SourcesViewModel,
    category: Category,
) {
    var searchQuery by remember { mutableStateOf("") }

    if (uiState.source.isEmpty() && uiState.error != null) {
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

    if (uiState.source.isEmpty() && uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            // TextField untuk pencarian
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                label = { Text("Search Sources") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        viewModel.loadSource(category, searchQuery, true)
                    }
            ) {
                Text(
                    modifier = Modifier
                        .padding(all = 10.dp),
                    text = "Search",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }



        LaunchedEffect(searchQuery) {
            kotlinx.coroutines.delay(500)

            viewModel.loadSource(category, searchQuery.ifEmpty { null }, true)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.source) {source ->
                SourceItem(source = source) {
                    navController.navigate("${NavRoutes.ARTICLE_LIST}/${it.id}")
                }
            }

            item {
                if (uiState.isLoading && uiState.source.isNotEmpty()) {
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

@Composable
fun SourceItem(source: Source, onItemClick: (Source) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(source) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = source.name)
            Text(text = source.description ?: "")
        }
    }
}