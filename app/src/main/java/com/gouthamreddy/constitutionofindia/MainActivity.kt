package com.gouthamreddy.constitutionofindia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gouthamreddy.constitutionofindia.ui.AppNavigation
import com.gouthamreddy.constitutionofindia.ui.theme.ConstitutionOfIndiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConstitutionOfIndiaTheme {
                AppNavigation()
            }
        }
    }
}