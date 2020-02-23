package com.jjouini.weather_app_demo.framework.api

import com.jjouini.weather_app_demo.core.model.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IOpenWeatherAPI {

    @GET("forecast")
    suspend fun forecast(@Query("q") cityName: String): Response<Forecast>
}