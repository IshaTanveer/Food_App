package com.example.foodapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.foodapp.pojo.CategoryItems
import com.example.foodapp.R
import com.example.foodapp.retrofit.NetworkResponse
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.viewModel.MealViewModel


@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    myViewModel: MealViewModel,
    onClick: (String) -> Unit
){
    LaunchedEffect(Unit) {
        myViewModel.getRandomMeal()
    }
    val randomMeal = myViewModel.randomMeal.observeAsState()
    val categoriesList = listOf(
        CategoryItems("Dessert", painterResource(id = R.drawable.dessert)),
        CategoryItems("Chicken", painterResource(id = R.drawable.chicken)),
        CategoryItems("Beef", painterResource(id = R.drawable.beef)),
        CategoryItems("vegetables", painterResource(id = R.drawable.vegetable)),
        CategoryItems("Sea Food", painterResource(id = R.drawable.seafood)),
        CategoryItems("Rice", painterResource(id = R.drawable.rice)),
        CategoryItems("Chaat", painterResource(id = R.drawable.chaat)),
        CategoryItems("Soup", painterResource(id = R.drawable.soup)),
        CategoryItems("Wraps", painterResource(id = R.drawable.wrap)),
        CategoryItems("Pasta", painterResource(id = R.drawable.pasta)),
        CategoryItems("Drinks", painterResource(id = R.drawable.drink)),
        CategoryItems("Bread", painterResource(id = R.drawable.bread))
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        item {
            Header()
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text(text = stringResource(R.string.What_would_you_like),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth().height(220.dp),
                contentAlignment = Alignment.Center
            ){
                when(val result = randomMeal.value){
                    is NetworkResponse.Failure -> {
                        Text(text = "Failed to load image.")
                    }
                    NetworkResponse.Loading -> {
                        CircularProgressIndicator()
                    }
                    is NetworkResponse.Success -> {
                        GlideImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onClick(result.data.meals[0].idMeal)
                                },
                            model = result.data.meals[0].strMealThumb,
                            contentDescription = stringResource(R.string.popular_items),
                            contentScale = ContentScale.Crop
                        )
                    }
                    null -> {}
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text(text = stringResource(R.string.popular_items),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Row{
                LazyRow(content = {
                    items(100, itemContent = {
                        GlideImage(
                            modifier = Modifier
                                .width(170.dp)
                                .height(120.dp)
                                .padding(end = 20.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {},
                            model = "",
                            contentDescription = stringResource(R.string.popular_items),
                            contentScale = ContentScale.Crop,
                            failure = placeholder(R.drawable.place_holder)
                        )
                    })
                })
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text(text = stringResource(R.string.categories),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            CategoryButtons(categoriesList)
        }
    }
}
@Composable
fun Header(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.home),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Red
        )
        Icon(imageVector = Icons.Default.Search,
            contentDescription = "Search",
            Modifier.size(40.dp)
        )
    }
}
@Composable
fun CategoryButtons(categoriesList: List<CategoryItems>){
    LazyHorizontalGrid(
        rows = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(categoriesList){ items ->
            Button(
                onClick = {},
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = items.image,
                        contentDescription = "Dessert",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = items.title,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )

                }
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun ShowHome(){
    FoodAppTheme {
    }
}