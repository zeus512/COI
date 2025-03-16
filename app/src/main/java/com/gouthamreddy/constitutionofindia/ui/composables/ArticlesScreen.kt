package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gouthamreddy.constitutionofindia.ui.ScreenState
import com.gouthamreddy.constitutionofindia.viewmodels.MainActivityViewModel


@Composable
fun ArticlesScreen( navigateTo: (ScreenState) -> Unit) {
    val viewModel: MainActivityViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.fetchCombinedJSONData()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.articlesList) { article ->
            InteractiveCard(
                title = article.title,
                onClick = { navigateTo(ScreenState.ArticleDetail(article.articleNumber)) })
        }
    }
}
