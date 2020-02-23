package com.jjouini.weather_app_demo.framework.repository

import com.jjouini.weather_app_demo.core.model.WeatherStatus
import com.jjouini.weather_app_demo.framework.api.OpenWeatherApiService

class ForecastRepository : BaseRepository() {
    suspend fun getForecast(cityName: String): MutableList<WeatherStatus>? {
        return safeApiCall(
            call = { OpenWeatherApiService.serviceApi.forecast(cityName) },
            error = "Error fetching news"
        )?.statusList?.toMutableList()
    }
}