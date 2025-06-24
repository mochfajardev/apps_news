package com.example.apps_news.persentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apps_news.data.model.Category

@Composable
fun CategoryListItem(
    modifier: Modifier = Modifier,
    items: List<Category>,
    onCategoryClick: (Unit) -> Unit,
) {
    LazyRow {
        items(items.size) { category ->
            val result = items[category]
            Box(
                modifier = modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Red)
                    .clickable { onCategoryClick(Unit) }

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

@Preview
@Composable
fun CategoryPreview() {
    val items: List<Category> = Category.entries
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CategoryListItem(
            items = items,
            onCategoryClick = {}
        )
    }

}