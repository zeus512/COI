package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gouthamreddy.constitutionofindia.ui.ScreenState

@Composable
fun AboutRow(navigateTo: (ScreenState) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { navigateTo(ScreenState.AboutScreen) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.Info, contentDescription = "About App")
        Spacer(modifier = Modifier.width(8.dp))
        Text("About App", style = MaterialTheme.typography.bodyMedium)
    }
}