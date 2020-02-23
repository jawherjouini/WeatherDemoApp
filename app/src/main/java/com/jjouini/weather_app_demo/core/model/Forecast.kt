package com.jjouini.weather_app_demo.core.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("city")
    var city: City?,
    @SerializedName("cnt")
    var count: Int?,
    @SerializedName("cod")
    var cod: String?,
    @SerializedName("list")
    var statusList: List<WeatherStatus>?,
    @SerializedName("message")
    var message: Int?
) : Parcelable