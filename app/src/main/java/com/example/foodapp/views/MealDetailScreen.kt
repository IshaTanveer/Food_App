package com.example.foodapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.foodapp.R
import com.example.foodapp.retrofit.MealByIdModel
import com.example.foodapp.retrofit.NetworkResponse
import com.example.foodapp.viewModel.MealViewModel

@Composable
fun MealDetailScreen(
    meadId: String,
    myViewModel: MealViewModel
){
    LaunchedEffect(Unit) {
        myViewModel.getMealById(meadId)
    }
    val mealById = myViewModel.mealById.observeAsState()
    when(val result = mealById.value){
        is NetworkResponse.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Failed to load Details!")
            }
        }
        NetworkResponse.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is NetworkResponse.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                item { MealImage(image = result.data.meals[0].strMealThumb) }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { Text(
                    text = result.data.meals[0].strMeal,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                ) }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                item { CenterRow(result = result.data) }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                item { Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                ) }
                item { Text(
                    text = result.data.meals[0].strInstructions,
                    style = MaterialTheme.typography.titleMedium
                ) }
            }
        }
        null -> {}
    }
}

@Composable
fun CenterRow(result: MealByIdModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = result.meals[0].strCategory,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = result.meals[0].strArea,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Text(
            buildAnnotatedString {
                result.meals[0].strYoutube.trim().replace("\\", "").let {
                    LinkAnnotation.Url(
                        it,
                        TextLinkStyles(style = SpanStyle(color = Color.Blue))
                    )
                }.let {
                    withLink(
                        it
                    ) {
                        append("YOUTUBE")
                    }
                }
            }
        )
        IconButton( onClick = {} ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "favourite",
                Modifier.size(35.dp)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MealImage(image: String) {
    GlideImage(
        modifier = Modifier
            .fillMaxWidth().height(300.dp)
            .clip(RoundedCornerShape(10.dp)),
        model = image,
        contentDescription = stringResource(R.string.popular_items),
        contentScale = ContentScale.Crop,
        //failure = placeholder(painter = painterResource(id = R.drawable.place_holder))
    )
}
