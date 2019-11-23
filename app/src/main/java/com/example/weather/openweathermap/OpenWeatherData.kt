package com.example.weather.openweathermap

data class WeatherWrapper(
    val base: String,
    val clouds: CloudsData,
    val cod: Int,
    val coord: CoordData,
    val dt: Int,
    val id: Int,
    val main: MainData,
    val name: String,
    val sys: SysData,
    val visibility: Int,
    val weather: List<WeatherData>,
    val wind: WindData
) {
    data class CloudsData(
        val all: Int
    )

    data class CoordData(
        val lat: Double,
        val lon: Double
    )

    data class MainData(
        val humidity: Int,
        val pressure: Int,
        val temp: Float,
        val temp_max: Double,
        val temp_min: Double
    )

    data class SysData(
        val country: String,
        val id: Int,
        val message: Double,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
    )

    data class WeatherData(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    )

    data class WindData(
        val deg: Int,
        val speed: Double
    )
}


