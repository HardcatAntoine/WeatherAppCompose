package com.example.weatherappcompose.data.model

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
) {
    fun toUIModel(): List<WeatherUIModel> {
        val list = ArrayList<WeatherUIModel>()
        if (this.toString().isEmpty()) return listOf()
        val days = this.forecast.forecastday
        for (element in days) {
            list.add(
                WeatherUIModel(
                    city = this.location.name,
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
            time = this.current.last_updated,
            currentTemp = this.current.temp_c.toString()
        )
        return list
    }
}