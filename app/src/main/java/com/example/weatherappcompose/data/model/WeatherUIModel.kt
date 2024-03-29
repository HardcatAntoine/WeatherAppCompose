package com.example.weatherappcompose.data.model

data class WeatherUIModel(
    val city: String,
    val time: String,
    val currentTemp: String,
    val condition: String,
    val icon: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: List<Hour>
)
