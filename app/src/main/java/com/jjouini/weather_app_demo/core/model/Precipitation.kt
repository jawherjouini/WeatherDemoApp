package com.jjouini.weather_app_demo.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Precipitation(@SerializedName("3h") var volumeLast3h: Double?) : Parcelable
