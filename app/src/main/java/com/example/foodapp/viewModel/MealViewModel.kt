package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.retrofit.CategoriesModel
import com.example.foodapp.retrofit.MealByIdModel
import com.example.foodapp.retrofit.NetworkResponse
import com.example.foodapp.retrofit.PopularMealModel
import com.example.foodapp.retrofit.RandomMealModel
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class MealViewModel: ViewModel() {

    private  val randomMealApi = RetrofitInstance.randomMealApi
    private val _randomMeal = MutableLiveData<NetworkResponse<RandomMealModel>>()
    val randomMeal:LiveData<NetworkResponse<RandomMealModel>> = _randomMeal

    private  val mealByIdApi = RetrofitInstance.mealByIdApi
    private val _mealById = MutableLiveData<NetworkResponse<MealByIdModel>>()
    val mealById:LiveData<NetworkResponse<MealByIdModel>> = _mealById

    private val popularMealApi = RetrofitInstance.popularMealApi
    private val _popularMeal = MutableLiveData<NetworkResponse<PopularMealModel>>()
    val popularMeal:LiveData<NetworkResponse<PopularMealModel>> = _popularMeal

    private val categoriesApi = RetrofitInstance.categoriesApi
    private val _categories = MutableLiveData<NetworkResponse<CategoriesModel>>()
    val categories:LiveData<NetworkResponse<CategoriesModel>> = _categories

    private val mealByCategoryApi = RetrofitInstance.popularMealApi
    private val _mealByCategory = MutableLiveData<NetworkResponse<PopularMealModel>>()
    val mealByCategory:LiveData<NetworkResponse<PopularMealModel>> = _mealByCategory

    fun getMealByCategory(category: String){
        viewModelScope.launch {
            _mealByCategory.value = NetworkResponse.Loading
            val response = mealByCategoryApi.getPopularMeal(category = category)
            if (response.isSuccessful){
                Log.i("My Meal By Category: ", response.body().toString())
                response.body()?.let {
                    _mealByCategory.value = NetworkResponse.Success(it)
                }
            }
            else{
                Log.i("My Meal By Category: My Error: ", response.message())
                Log.i("My Meal By Category: API Error Code", response.code().toString())
                Log.i("My Meal By Category: API Error Message", response.message())
                Log.i("My Meal By Category: API Error Body", response.errorBody()?.string() ?: "null")
                _mealByCategory.value = NetworkResponse.Failure("Failed to load meal!")
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            _categories.value = NetworkResponse.Loading
            val response = categoriesApi.getCategories()
            if (response.isSuccessful){
                Log.i("My Response: ", response.body().toString())
                response.body()?.let {
                    _categories.value = NetworkResponse.Success(it)
                }
            }
            else{
                Log.i("My Error: ", response.message())
                Log.i("API Error Code", response.code().toString())
                Log.i("API Error Message", response.message())
                Log.i("API Error Body", response.errorBody()?.string() ?: "null")
                _categories.value = NetworkResponse.Failure("Failed to load!")
            }
        }
    }

    fun getPopularMeal(){
        viewModelScope.launch {
            _randomMeal.value = NetworkResponse.Loading
            val response = popularMealApi.getPopularMeal(category = "Seafood")
            if (response.isSuccessful){
                Log.i("My Response: ", response.body().toString())
                response.body()?.let {
                    _popularMeal.value = NetworkResponse.Success(it)
                }
            }
            else{
                Log.i("My Error: ", response.message())
                Log.i("API Error Code", response.code().toString())
                Log.i("API Error Message", response.message())
                Log.i("API Error Body", response.errorBody()?.string() ?: "null")
                _popularMeal.value = NetworkResponse.Failure("Failed to load meal!")
            }
        }
    }

    fun getRandomMeal(){
        viewModelScope.launch {
            _randomMeal.value = NetworkResponse.Loading
            val response = randomMealApi.getRandomMeal()
            if (response.isSuccessful){
                Log.i("My Response: ", response.body().toString() )
                response.body()?.let {
                    _randomMeal.value = NetworkResponse.Success(it)
                }
            }
            else{
                Log.i("My Error: ", response.message())
                Log.i("API Error Code", response.code().toString())
                Log.i("API Error Message", response.message())
                Log.i("API Error Body", response.errorBody()?.string() ?: "null")
                _randomMeal.value = NetworkResponse.Failure("Failed to Load data.")
            }
        }
    }
    fun getMealById(mealId: String){
        viewModelScope.launch {
            _mealById.value = NetworkResponse.Loading
            val response = mealByIdApi.getMealById(mealId)
            try {
                if (response.isSuccessful){
                    Log.i("My Meal Details: ", response.body().toString() )
                    response.body()?.let {
                        _mealById.value = NetworkResponse.Success(it)
                    }
                }
                else{
                    Log.i("My Error: ", response.message())
                    Log.i("API Error Code", response.code().toString())
                    Log.i("API Error Message", response.message())
                    Log.i("API Error Body", response.errorBody()?.string() ?: "null")
                    _mealById.value = NetworkResponse.Failure("Failed to load Detail!")
                }
            }catch (e: Exception){
                _mealById.value = NetworkResponse.Failure("Failed to Load data.")
            }
        }
    }
}