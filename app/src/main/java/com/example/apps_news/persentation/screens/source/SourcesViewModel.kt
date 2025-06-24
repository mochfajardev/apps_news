package com.example.apps_news.persentation.screens.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apps_news.data.model.Category
import com.example.apps_news.data.model.Source
import com.example.apps_news.domain.usecase.GetSourcesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SourceUiState(
    val source: List<Source> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)


class SourcesViewModel(
    private val getSourcesUseCase: GetSourcesUseCase
):ViewModel() {
    private val _uiState = MutableStateFlow(SourceUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    fun loadSource(category: Category?, query: String? = null, reset: Boolean = false) {
        if ((_uiState.value.isLoading || _uiState.value.endReached) && !reset) {
            return
        }
        viewModelScope.launch {
            val currentPage = if (reset) 1 else _uiState.value.page

            _uiState.update { it.copy(isLoading = true, error = null) }

            val response = getSourcesUseCase.invoke(category, currentPage, query)
            response.fold(
                onSuccess = { newArticles ->
                    _uiState.update { currentState ->
                        val sources = if (reset) newArticles else currentState.source + newArticles
                        currentState.copy(
                            isLoading = false,
                            source = sources,
                            endReached = newArticles.isEmpty(),
                            page = if (reset) 2 else currentState.page + 1,
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