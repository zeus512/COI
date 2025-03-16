package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gouthamreddy.constitutionofindia.ui.ScreenState
import com.gouthamreddy.constitutionofindia.viewmodels.MainActivityViewModel

@Composable
fun ArticleDetailScreen(articleNumber: String, navigateTo: (ScreenState) -> Unit) {
    val viewModel = hiltViewModel<MainActivityViewModel>()
    val state = viewModel.state.collectAsState().value
    val articleData = state.articlesList.find { it.articleNumber == articleNumber }
        ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = articleData.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000080)
        )
        Text(
            text = "Part: ${articleData.part}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Article Number: ${articleData.articleNumber}",
            fontSize = 18.sp
        )
        Text(
            text = "Summary: ${articleData.summary}",
            fontSize = 16.sp
        )
        Text(
            text = "Version 1:\n${articleData.version1}",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = "Version 2:\n${articleData.version2}",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )
    }
}
