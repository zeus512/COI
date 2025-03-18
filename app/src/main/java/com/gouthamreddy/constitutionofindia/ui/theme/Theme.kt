package com.gouthamreddy.constitutionofindia.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext



// 🇮🇳 Tri-color Gradient Colors
val GradientColors = listOf(Color(0xFFFF9933), Color.White, Color(0xFF138808))

// 🌙 Dark Mode Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF138808),  // Dark mode primary (Green)
    secondary = Color(0xFF000080), // Dark mode secondary (Navy Blue)
    background = Color(0xFF121212), // Dark mode background
    surface = Color(0xFF1E1E1E),    // Dark mode surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

// ☀️ Light Mode Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF138808),  // Light mode primary (Green)
    secondary = Color(0xFF000080), // Light mode secondary (Navy Blue)
    background = Color(0xFFFFFBFE), // Light mode background (White)
    surface = Color.White,         // Light mode surface
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ConstitutionOfIndiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
