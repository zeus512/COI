package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gouthamreddy.constitutionofindia.data.models.SearchResult
import com.gouthamreddy.constitutionofindia.viewmodels.MainActivityViewModel

@Composable
fun SearchResultsScreen(
    viewModel: MainActivityViewModel = hiltViewModel<MainActivityViewModel>(),
    navigateToDetail: (SearchResult) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.search(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search articles, schedules, amendments...") },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        viewModel.search("")
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Search")
                    }
                }
            },
            colors = TextFieldDefaults.colors().copy(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )

        // Search Results List
        if (state.searchResults.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(bottom = 14.dp)) {
                items(state.searchResults) { result ->
                    when (result) {
                        is SearchResult.ArticleResult -> {
                            SearchResultCard(
                                title = "Article ${result.article.articleNumber}: ${result.article.title}",
                                description = result.article.summary,
                                onClick = { navigateToDetail(result) }
                            )
                        }

                        is SearchResult.ScheduleResult -> {
                            SearchResultCard(
                                title = "Schedule ${result.schedule.title}",
                                description = result.schedule.articles + " - " + result.schedule.details,
                                onClick = { navigateToDetail(result) }
                            )
                        }

                        is SearchResult.AmendmentResult -> {
                            SearchResultCard(
                                title = "Amendment ${result.amendment.amendmentNumber} (${result.amendment.year})",
                                description = result.amendment.content,
                                onClick = { navigateToDetail(result) }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SearchResultCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
