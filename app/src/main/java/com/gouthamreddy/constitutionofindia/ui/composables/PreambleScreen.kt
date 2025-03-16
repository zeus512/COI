package com.gouthamreddy.constitutionofindia.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreambleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "WE, THE PEOPLE OF INDIA, having solemnly resolved to constitute India into a SOVEREIGN, SOCIALIST, SECULAR, DEMOCRATIC, REPUBLIC and to secure to all its citizens: Justice, social, economic and political; Liberty of thought, expression, belief, faith and worship; Equality of status and of opportunity; and to promote among them all fraternity assuring the dignity of the individual and the unity and integrity of the Nation.",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000080)
        )
        Text(
            text = "A declaration of India's fundamental principles...",
            fontSize = 18.sp
        )
    }
}
