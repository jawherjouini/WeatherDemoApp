package com.jjouini.weather_app_demo.core.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main(
    @SerializedName("feels_like")
    var feelsLike: Double?,
    @SerializedName("grnd_level")
    var groundLevel: Int?,
    @SerializedName("humidity")
    var humidity: Int?,
    @SerializedName("pressure")
    var pressure: Int?,
    @SerializedName("sea_level")
    var seaLevel: Int?,
    @SerializedName("temp")
    var temp: Double?,
    @SerializedName("temp_kf")
    var tempKf: Double?,
    @SerializedName("temp_max")
    var tempMax: Double?,
    @SerializedName("temp_min")
    var tempMin: Double?
) : Parcelable