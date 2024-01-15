package com.example.weatherappcompose.data.repo

import com.example.weatherappcompose.data.model.WeatherResponse
import com.example.weatherappcompose.data.remote.ApiService
import com.example.weatherappcompose.utils.ALERTS
import com.example.weatherappcompose.utils.AQI
import com.example.weatherappcompose.utils.DAYS
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getWeatherResponse(city: String): WeatherResponse {
        return apiService.getWeatherResponse(city, DAYS, AQI, ALERTS)
    }
}