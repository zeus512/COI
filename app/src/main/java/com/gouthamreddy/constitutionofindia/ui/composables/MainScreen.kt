package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gouthamreddy.constitutionofindia.ui.ScreenState
import com.gouthamreddy.constitutionofindia.viewmodels.MainActivityViewModel

@Composable
fun MainScreen(
    viewModel: MainActivityViewModel = hiltViewModel<MainActivityViewModel>(),
    navigateTo: (ScreenState) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.fetchDataFromRemote()
    }
    val sections = listOf(
        ScreenState.Parts,
        ScreenState.Preamble,
        ScreenState.Schedules,
        ScreenState.Amendments
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Constitution of India",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(20.dp))
        sections.forEach { section ->
            InteractiveCard(
                title = section.toString(),
                onClick = { navigateTo(section) })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter, modifier = Modifier
            .fillMaxSize()
    ) {
        AboutRow { navigateTo(ScreenState.AboutScreen) }
    }

}