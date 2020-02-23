package com.jjouini.weather_app_demo.core.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cloudiness(
    @SerializedName("all")
    var percent: Int?
) : Parcelable