package com.example.apps_news.di

import com.example.apps_news.persentation.screens.article.ArticlesViewModel
import com.example.apps_news.persentation.screens.category.CategoryViewModel
import com.example.apps_news.persentation.screens.source.SourcesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryViewModel(get()) }
    viewModel { ArticlesViewModel(get()) }
    viewModel { SourcesViewModel(get()) }
}