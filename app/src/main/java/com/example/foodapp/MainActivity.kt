package com.example.foodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.mains.MainScreen
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.viewModel.MealViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var viewModel = ViewModelProvider(this)[MealViewModel::class.java]
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            FoodAppTheme {
                MainScreen(
                    myViewModel = viewModel
                )
            }
        }
    }
}