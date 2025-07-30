package com.example.foodapp.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealByIdApi {
    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ):Response<MealByIdModel>
}