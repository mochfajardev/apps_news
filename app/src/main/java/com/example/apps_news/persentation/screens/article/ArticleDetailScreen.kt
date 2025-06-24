package com.example.apps_news.persentation.screens.article

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ArticleDetailScreen(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.domStorageEnabled = true
            loadUrl(url)
        }
    }, update = { webView ->
        if (webView.url != url) {
            webView.loadUrl(url)
        }
    })
}