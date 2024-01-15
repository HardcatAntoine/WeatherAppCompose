package com.example.weatherappcompose.data.model

import com.example.weatherappcompose.data.model.Hour

data class WeatherData(
    val city: String,
    val time: String,
    val currentTemp: String,
    val condition: String,
    val icon: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: List<Hour>
)
