package com.example.foodapp.mains

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodapp.viewModel.MealViewModel
import com.example.foodapp.views.CategoriesScreen
import com.example.foodapp.views.FavouritesScreen
import com.example.foodapp.views.HomeScreen

@Composable
fun NavTab(modifier: Modifier = Modifier, selectedIndex: Int, myViewModel: MealViewModel){
    when(selectedIndex){
        0 -> HomeScreen(modifier, myViewModel, onClick = {} )
        1 -> FavouritesScreen(modifier)
        2 -> CategoriesScreen(modifier)
    }
}