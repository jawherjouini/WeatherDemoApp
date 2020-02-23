package com.jjouini.weather_app_demo.core.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jjouini.weather_app_demo.R
import com.jjouini.weather_app_demo.core.model.WeatherStatus
import com.jjouini.weather_app_demo.framework.util.ForecastUtils
import com.jjouini.weather_app_demo.framework.util.Units
import kotlinx.android.synthetic.main.item_detail.view.*
import kotlin.math.roundToInt

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ForecastWeatherStatusListActivity]
 * in two-pane mode (on tablets) or a [ForecastWeatherStatusDetailActivity]
 * on handsets.
 */
class ForecastWeatherStatusDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: WeatherStatus? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_LIST_ITEM)) {
                item = it.getParcelable(ARG_LIST_ITEM)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        item?.let {
            // Clouds
            rootView.dv_clouds.setTitle(resources.getString(R.string.forecast_detail_cloud_label))
            rootView.dv_clouds.setFirstRow(
                item?.cloudiness?.percent.toString(),
                Units.PERCENT_UNIT,
                R.drawable.ic_cloud
            )

            // Wind
            rootView.dv_wind.setTitle(resources.getString(R.string.forecast_detail_wind_label))
            rootView.dv_wind.setFirstRow(
                item?.wind?.speed.toString(),
                Units.SPEED_UNIT,
                R.drawable.ic_wind
            )
            rootView.dv_wind.setSecondRow(
                item?.wind?.degree.toString(),
                Units.DEGREE_UNIT,
                R.drawable.ic_windsock
            )

            // Temperature
            when (ForecastUtils.getStatus(item?.main?.temp)) {
                -1 -> {
                    rootView.dv_temp.setTitle(resources.getString(R.string.forecast_detail_temp_cold_label))
                    rootView.dv_temp.setBlueTitle()
                }
                1 -> {
                    rootView.dv_temp.setTitle(resources.getString(R.string.forecast_detail_temp_hot_label))
                    rootView.dv_temp.setRedTitle()
                }
                else -> {
                    rootView.dv_temp.setTitle(resources.getString(R.string.forecast_detail_temp_label))
                }
            }
            rootView.dv_temp.setFirstRow(
                String.format(
                    resources.getString(R.string.temperature_feels_like_label),
                    (item?.main?.feelsLike!!).roundToInt().toString()
                ), Units.TEMP_UNIT, R.drawable.ic_temp
            )
            rootView.dv_temp.setSecondRow(
                (item?.main?.tempMin!!).roundToInt().toString(),
                Units.TEMP_UNIT,
                R.drawable.ic_temp_min
            )
            rootView.dv_temp.setThirdRow(
                (item?.main?.tempMax!!).roundToInt().toString(),
                Units.TEMP_UNIT,
                R.drawable.ic_temp_max
            )

            // Pressure
            rootView.dv_pressure.setTitle(resources.getString(R.string.forecast_detail_pressure_label))
            rootView.dv_pressure.setFirstRow(
                item?.main?.seaLevel.toString(),
                Units.PRESSURE_UNIT,
                R.drawable.ic_sea
            )
            rootView.dv_pressure.setSecondRow(
                item?.main?.groundLevel.toString(),
                Units.PRESSURE_UNIT,
                R.drawable.ic_ground
            )

            // Humidity
            rootView.dv_humidity.setTitle(resources.getString(R.string.forecast_detail_humidity_label))
            rootView.dv_humidity.setFirstRow(
                item?.main?.humidity.toString(),
                Units.PERCENT_UNIT,
                R.drawable.ic_humidity
            )
        }
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_LIST_ITEM = "com.jjouini.weather_app_demo.item"
    }
}
