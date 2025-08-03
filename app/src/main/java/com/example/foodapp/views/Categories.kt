package com.example.foodapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.foodapp.R
import com.example.foodapp.retrofit.NetworkResponse
import com.example.foodapp.viewModel.MealViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Categories(
    mealCategory: String,
    myViewModel: MealViewModel,
    onClick:(String) -> Unit
){
    LaunchedEffect(mealCategory) {
        myViewModel.getMealByCategory(mealCategory)
    }
    val mealByCategory = myViewModel.mealByCategory.observeAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth().padding(vertical = 20.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        when(val result = mealByCategory.value){
            is NetworkResponse.Failure -> {
                item {
                    Text(text = "Failed to load!")
                }
            }
            NetworkResponse.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }
                }
            }
            is NetworkResponse.Success -> {
                items(result.data.meals){ items ->
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        GlideImage(
                            modifier = Modifier.fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onClick(items.idMeal)
                                },
                            model = items.strMealThumb,
                            contentDescription = stringResource(R.string.categories),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = items.strMeal,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            null -> {}
        }
    }
}
