package com.example.foodapp.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface RandomMealApi {
    @GET("random.php")
    suspend fun getRandomMeal():Response<RandomMealModel>
}