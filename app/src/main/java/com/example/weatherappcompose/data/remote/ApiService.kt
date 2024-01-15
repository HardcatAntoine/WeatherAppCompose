package com.example.weatherappcompose.data.remote

import com.example.weatherappcompose.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(value = "forecast.json")
    suspend fun getWeatherResponse(
        @Query("q") city: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): WeatherResponse
}