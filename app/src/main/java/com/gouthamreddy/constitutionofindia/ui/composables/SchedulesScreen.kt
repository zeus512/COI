package com.gouthamreddy.constitutionofindia.ui.composables

import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import com.gouthamreddy.constitutionofindia.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesScreen() {
    val viewModel = hiltViewModel<MainActivityViewModel>()
    val schedules = viewModel.state.collectAsState().value.schedulesList

    var showWebView by remember { mutableStateOf(false) }
    var selectedUrl by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(schedules) { schedule ->
            ScheduleItem(schedule = schedule, onDetailClick = {
                selectedUrl = it
                showWebView = true

            })
        }
    }
    if (showWebView) {
        ModalBottomSheet(
            onDismissRequest = {
                showWebView = false
                scope.launch { sheetState.hide() }
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize().nestedScroll(rememberNestedScrollInteropConnection())
        ) {
            // Sheet content
            WebViewScreen(url = selectedUrl, onClose = {
                showWebView = false
                scope.launch { sheetState.hide() }
            }, webView = webView)
        }

    }
}

@Composable
fun ScheduleItem(schedule: ScheduleEntity, onDetailClick: (String) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Display the title
            Text(
                text = schedule.title,
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                fontWeight = FontWeight.Bold
            )

            // Display the articles
            Text(
                text = "Articles: ${schedule.articles}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                modifier = Modifier.padding(top = 8.dp)
            )

            // Display the details
            if (schedule.details.isNotEmpty()) {
                schedule.details.forEach { detail ->
                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        Text(
                            text = detail.text,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                        )
                        Text(
                            text = "Open Detail",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Blue,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .clickable {
                                    onDetailClick(detail.url)
                                }
                        )
                    }
                }
            }
        }
    }
}