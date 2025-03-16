package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SchedulesScreen() {
    val schedules =
        listOf("Schedule I: States & UTs", "Schedule II: Salaries", "Schedule III: Oaths")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(schedules) { schedule ->
            InteractiveCard(title = schedule, onClick = {})
        }
    }
}


