package com.example.foodapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private const val baseUrl = "https://www.themealdb.com/api/json/v1/1/"
    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val randomMealApi: MealApi = getInstance().create(MealApi::class.java)
    val mealByIdApi: MealApi = getInstance().create(MealApi::class.java)
    val popularMealApi: MealApi = getInstance().create(MealApi::class.java)
}