package com.jjouini.weather_app_demo.framework.util

import com.jjouini.weather_app_demo.R
import com.jjouini.weather_app_demo.core.model.WeatherStatus
import java.util.*

object ForecastUtils {

    fun getStatus(temperature: Double?): Int {
        if (temperature == null)
            return 0
        if (temperature < 10)
            return -1
        if (temperature > 15)
            return 1
        return 0
    }

    fun getBackgroundDrawable(item: WeatherStatus?): Int {
        var result = 0
        if (item != null) {
            when {
                (item.weather)?.get(0)?.description?.toLowerCase(Locale.getDefault())?.contains("cloud")!! -> {
                    result = R.drawable.item_bg_cloud
                }
                (item.weather)?.get(0)?.description?.toLowerCase(Locale.getDefault())?.contains("clear")!! -> {
                    result = R.drawable.item_bg_clear
                }
                (item.weather)?.get(0)?.description?.toLowerCase(Locale.getDefault())?.contains("mist")!! -> {
                    result = R.drawable.item_bg_mist
                }
                (item.weather)?.get(0)?.description?.toLowerCase(Locale.getDefault())?.contains("rain")!! -> {
                    result = R.drawable.item_bg_rainy
                }
                (item.weather)?.get(0)?.description?.toLowerCase(Locale.getDefault())?.contains("snow")!! -> {
                    result = R.drawable.item_bg_snow
                }
                (item.weather)?.get(0)?.description?.toLowerCase(Locale.getDefault())?.contains("thunderstorm")!! -> {
                    result = R.drawable.item_bg_tstorm
                }
            }
        }
        return result
    }
}
