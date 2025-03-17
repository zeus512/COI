package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gouthamreddy.constitutionofindia.ui.ScreenState
import com.gouthamreddy.constitutionofindia.viewmodels.MainActivityViewModel

@Composable
fun ArticleDetailScreen(
    articleNumber: String,
    viewModel: MainActivityViewModel = hiltViewModel<MainActivityViewModel>(),
    navigateTo: (ScreenState) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val articleData = state.articlesList.find { it.articleNumber == articleNumber } ?: return

    var showVersion1 by remember { mutableStateOf(false) }
    var showVersion2 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Title Card
        CardView {
            Text(
                text = articleData.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        // Part & Article Info
        CardView { InfoRow(label = "Part", value = articleData.part) }
        CardView { InfoRow(label = "Article Number", value = articleData.articleNumber) }

        // Summary Section
        CardView {
            Column {
                SectionTitle("Summary")
                Text(text = articleData.summary, fontSize = 16.sp, color = Color.Black)
            }
        }

        // Expandable Version 1
        ExpandableCard(
            title = "Version 1",
            content = articleData.version1,
            isExpanded = showVersion1,
            onToggle = { showVersion1 = !showVersion1 }
        )

        // Expandable Version 2
        ExpandableCard(
            title = "Version 2",
            content = articleData.version2,
            isExpanded = showVersion2,
            onToggle = { showVersion2 = !showVersion2 }
        )
    }
}

// 🔹 Reusable Card Wrapper
@Composable
fun CardView(content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            content()
        }
    }
}

// 🔹 Section Title
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF000080),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

// 🔹 Key-Value Display
@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "$label:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF000080)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

// 🔹 Expandable Card for Versions
@Composable
fun ExpandableCard(title: String, content: String, isExpanded: Boolean, onToggle: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionTitle(title)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
            if (isExpanded) {
                Text(
                    text = content,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

