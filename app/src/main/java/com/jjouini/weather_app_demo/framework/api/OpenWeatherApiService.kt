package com.jjouini.weather_app_demo.framework.api

import com.google.gson.GsonBuilder
import com.jjouini.weather_app_demo.BuildConfig
import com.jjouini.weather_app_demo.framework.util.Units.JSON_DATETIME_FORMAT
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherApiService {

    val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val interceptor = Interceptor { chain ->
        val url: HttpUrl = chain.request().url().newBuilder()
            .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
            .addQueryParameter("mode", "json")
            .addQueryParameter("units", "metric")
            .build()
        val request = chain.request().newBuilder().addHeader(
            "Accept",
            "application/json"
        ).url(url).build()
        chain.proceed(request)
    }
    val gson = GsonBuilder()
        .setDateFormat(JSON_DATETIME_FORMAT)
        .create()

    val apiClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(apiClient)
        }.build()
    }

    val serviceApi: IOpenWeatherAPI = getRetrofit().create(IOpenWeatherAPI::class.java)
}