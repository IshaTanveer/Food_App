package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.retrofit.MealByIdModel
import com.example.foodapp.retrofit.NetworkResponse
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