package com.example.weatherappcompose.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.data.model.WeatherData
import com.example.weatherappcompose.data.model.WeatherResponse
import com.example.weatherappcompose.data.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    val daysList = mutableStateOf(listOf<WeatherData>())
    val dialogState = mutableStateOf(false)
    val currentDay =
        mutableStateOf(
            WeatherData(
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
            val list = getWeatherDataForUI(response)
            currentDay.value = list[0]
            daysList.value = list
        }
    }

    private fun getWeatherDataForUI(response: WeatherResponse): List<WeatherData> {
        val list = ArrayList<WeatherData>()
        if (response.toString().isEmpty()) return listOf()
        val days = response.forecast.forecastday
        for (element in days) {
            list.add(
                WeatherData(
                    city = response.location.name,
                    time = element.date,
                    currentTemp = "",
                    condition = element.day.condition.text,
                    icon = element.day.condition.icon,
                    maxTemp = element.day.maxtemp_c.toString(),
                    minTemp = element.day.mintemp_c.toString(),
                    hours = element.hour
                )
            )
        }
        list[0] = list[0].copy(
            time = response.current.last_updated,
            currentTemp = response.current.temp_c.toString()
        )
        return list
    }
}
