package com.example.foodapp.mains

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.foodapp.pojo.NavItems
import com.example.foodapp.viewModel.MealViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.views.CategoriesScreen
import com.example.foodapp.views.FavouritesScreen
import com.example.foodapp.views.HomeScreen
import com.example.foodapp.views.MealDetailScreen

@Composable
fun MainScreen(myViewModel: MealViewModel){
    val navList = listOf(
        NavItems("Home", Icons.Default.Home),
        NavItems("Favourites", Icons.Default.FavoriteBorder),
        NavItems("Categories", Icons.Default.MoreVert)
    )

    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf("home", "favourites", "categories")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar){
                NavigationBar {
                    navList.forEachIndexed { index, navItems ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                //selectedIndex = index
                                selectedIndex = index
                                when (index) {
                                    0 -> navController.navigate("home") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                    1 -> navController.navigate("favourites") {
                                        popUpTo("home")
                                    }
                                    2 -> navController.navigate("categories") {
                                        popUpTo("home")
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItems.image,
                                    contentDescription = "Icon"
                                )
                            },
                            label = {
                                Text(text = navItems.title)
                            }
                        )
                    }
                }
            }
        }
        ){ innerPadding ->
        //NavTab(modifier = Modifier.padding(innerPadding), selectedIndex, myViewModel)
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(myViewModel = myViewModel, onClick = { mealId ->
                    navController.navigate("detail/$mealId")
                })
            }
            composable("favourites") {
                FavouritesScreen()
            }
            composable("categories") {
                CategoriesScreen()
            }
            composable("detail/{mealId}") { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId")
                if (mealId != null) {
                    MealDetailScreen(meadId = mealId)
                }
            }
        }
    }
}
