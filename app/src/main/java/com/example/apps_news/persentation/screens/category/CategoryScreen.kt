package com.example.apps_news.persentation.screens.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apps_news.data.model.Category
import com.example.apps_news.persentation.screens.source.SourceScreen
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navController: NavHostController
) {
    val categories = viewModel.categories
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(categories) {
        if (categories.isNotEmpty() && selectedCategory == null) {
            selectedCategory = categories.first()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "News Apps",
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
                LazyRow {
                    items(categories.size) { category ->
                        val result = categories[category]
                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedCategory == result) MaterialTheme.colorScheme.primary else Color.Red)
                                .clickable {
                                    selectedCategory = result
                                }

                        ) {
                            Text(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .padding(all = 10.dp),
                                text = result.value,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )

                        }
                        Spacer(Modifier.padding(
                            horizontal = 6.dp
                        ))
                    }
                }
            }
            selectedCategory?.let { category ->
                key(category) {
                    SourceScreen(category = category, navController = navController)
                }
            }
        }


    }
}



