package com.example.weatherappcompose.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.data.model.WeatherUIModel
import com.example.weatherappcompose.data.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    val daysList = mutableStateOf(listOf<WeatherUIModel>())
    val dialogState = mutableStateOf(false)
    val currentDay =
        mutableStateOf(
            WeatherUIModel(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                listOf()
            )
        )


    fun getWeatherResponse(city: String) {
        viewModelScope.launch {
            val response = repository.getWeatherResponse(city)
            val list = response.toUIModel()
            currentDay.value = list[0]
            daysList.value = list
        }
    }
}
