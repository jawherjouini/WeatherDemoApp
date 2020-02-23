package com.jjouini.weather_app_demo.core.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class WeatherStatus(
    @SerializedName("clouds")
    var cloudiness: Cloudiness?,
    @SerializedName("dt")
    var dt: Long?,
    @SerializedName("dt_txt")
    var dtTxt: Date?,
    @SerializedName("main")
    var main: Main?,
    @SerializedName("rain")
    var rain: Precipitation?,
    @SerializedName("snow")
    var snow: Precipitation?,
    @SerializedName("sys")
    var sys: Sys?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind")
    var wind: Wind?
) : Parcelable