package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gouthamreddy.constitutionofindia.ui.ScreenState
import com.gouthamreddy.constitutionofindia.viewmodels.MainActivityViewModel

@Composable
fun PartsScreen(viewModel: MainActivityViewModel = hiltViewModel<MainActivityViewModel>(), navigateTo: (ScreenState) -> Unit) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val parts = state.articlesList.map { it.part }.distinct()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(parts) { part ->
            InteractiveCard(
                title = part,
                onClick = { navigateTo(ScreenState.Articles(part = part)) })
        }
    }
}
