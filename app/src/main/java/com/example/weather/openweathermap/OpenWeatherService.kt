package com.example.weather.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "379ba99fdbd78eb792e9d4cbcd00459d"

interface OpenWeatherService {

    @GET("data/2.5/weather?unit=metric&lang=fr")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY
    ): Call<WeatherWrapper>
}