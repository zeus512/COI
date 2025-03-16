package com.gouthamreddy.constitutionofindia.ui.composables

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String, onClose: () -> Unit, webView: WebView) {
    // Update the URL whenever the url parameter changes
    LaunchedEffect(key1 = url) {
        webView.loadUrl(url)
    }

    // Handle back button press
    BackHandler {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onClose()
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            webView.apply {
                settings.javaScriptEnabled = true // Enable JavaScript if needed
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        // You can perform actions here when the page finishes loading
                    }
                }
                loadUrl(url)
            }
        })
}
