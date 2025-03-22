package com.gouthamreddy.constitutionofindia.ui.composables

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.gouthamreddy.constitutionofindia.ui.theme.ConstitutionOfIndiaTheme

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // App Name
        Text(
            text = "Constitution of India",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        // App Version (optional)
        Text(
            text = "Version: 1.0",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // Disclaimer
        Text(
            text = "Disclaimer",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "This app is an independent educational resource and is not affiliated with any government entity. The information provided in this app is intended for general knowledge and informational purposes only.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Source of Information",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "The content in this app is compiled from publicly available information, including official government websites and publications. While we strive to ensure accuracy, we cannot guarantee that the information is entirely free of errors.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Data Privacy",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "This app does not collect any personal information from users. It does not require any user login or account creation.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Privacy Policy",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "You can view our full privacy policy here:",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = {
            val privacyPolicyUrl = "https://pixsee.in/coi/privacy-policy.html"
            val intent = Intent(Intent.ACTION_VIEW, privacyPolicyUrl.toUri())
            context.startActivity(intent)
        }) {
            Text(text = "View Privacy Policy")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = "If you have any questions or feedback about this app, please contact us at:",
            style = MaterialTheme.typography.bodyMedium
        )
        Button(onClick = {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:contact@gouthamreddy.com".toUri()
                putExtra(Intent.EXTRA_SUBJECT, "Constitution of India App Feedback")
            }
            context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }) {
            Text(text = "Contact Us")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    ConstitutionOfIndiaTheme {
        AboutScreen()
    }
}