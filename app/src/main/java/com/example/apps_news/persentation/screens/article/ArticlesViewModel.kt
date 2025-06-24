package com.example.apps_news.persentation.screens.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apps_news.data.model.Article
import com.example.apps_news.domain.usecase.GetArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ArticleUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)


class ArticlesViewModel(
    private val getArticlesUseCase: GetArticlesUseCase
):ViewModel() {
    private val _uiState = MutableStateFlow(ArticleUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()


    fun loadArticles(sourceId: String, query: String? = null) {
//        if (_uiState.value.isLoading || _uiState.value.endReached) {
//            return
//        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentPage = _uiState.value.page
            val response = getArticlesUseCase.invoke(sourceId, currentPage, query)

            response.fold(
                onSuccess = { newArticles ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            articles = currentState.articles + newArticles,
                            endReached = newArticles.isEmpty(),
                            page = currentState.page + 1,
                            error = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.localizedMessage ?: "Unknown Error"
                        )
                    }
                }
            )
        }
    }

}