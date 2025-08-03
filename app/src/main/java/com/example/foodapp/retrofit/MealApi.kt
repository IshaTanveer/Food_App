package com.example.foodapp.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    suspend fun getRandomMeal():Response<RandomMealModel>

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ):Response<MealByIdModel>

    @GET("filter.php")
    suspend fun getPopularMeal(
        @Query("c") category: String
    ):Response<PopularMealModel>

    @GET("categories.php")
    suspend fun getCategories():Response<CategoriesModel>

}