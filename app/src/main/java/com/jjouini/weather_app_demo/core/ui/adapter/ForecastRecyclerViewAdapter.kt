package com.jjouini.weather_app_demo.core.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjouini.weather_app_demo.R
import com.jjouini.weather_app_demo.core.model.WeatherStatus
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusDetailActivity
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusDetailFragment
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusListActivity
import com.jjouini.weather_app_demo.framework.util.ForecastUtils
import com.jjouini.weather_app_demo.framework.util.Units
import com.jjouini.weather_app_demo.framework.util.Units.DATE_FORMAT_LONG
import com.jjouini.weather_app_demo.framework.util.Units.TIME_FORMAT_LONG
import kotlinx.android.synthetic.main.item_list_content.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class ForecastRecyclerViewAdapter(
    private val parentActivity: ForecastWeatherStatusListActivity,
    private val values: List<WeatherStatus>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<ForecastRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as WeatherStatus
            if (twoPane) {
                val fragment = ForecastWeatherStatusDetailFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putParcelable(ForecastWeatherStatusDetailFragment.ARG_LIST_ITEM, item)
                        }
                    }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val bundle = Bundle()
                bundle.putParcelable(ForecastWeatherStatusDetailFragment.ARG_LIST_ITEM, item)
                val intent =
                    Intent(v.context, ForecastWeatherStatusDetailActivity::class.java).apply {
                        putExtra(ForecastWeatherStatusListActivity.APP_BUNDLE, bundle)
                    }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormatter = SimpleDateFormat(DATE_FORMAT_LONG, Locale.getDefault())
        val timeFormatter = SimpleDateFormat(TIME_FORMAT_LONG, Locale.getDefault())

        val item = values[position]
        holder.tvDate.text = dateFormatter.format(item.dtTxt!!)
        holder.tvTime.text = timeFormatter.format(item.dtTxt!!)

        holder.tvTempMax.text =
            String.format(Units.TEMP_UNIT, (item.main?.tempMax!!).roundToInt())
        holder.tvTempMin.text =
            String.format(Units.TEMP_UNIT, (item.main?.tempMin!!).roundToInt())

        holder.bgWeather.setBackgroundResource(ForecastUtils.getBackgroundDrawable(item))

        val icon: String = item.weather?.get(0)?.icon!!
        val iconUrl = "https://openweathermap.org/img/w/$icon.png"

        Glide.with(parentActivity).load(iconUrl).into(holder.ivWeatherIcon)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bgWeather: ImageView = view.bg_weather
        val tvDate: TextView = view.tv_date
        val tvTime: TextView = view.tv_time
        val tvTempMax: TextView = view.tv_temp_max
        val tvTempMin: TextView = view.tv_temp_min
        val ivWeatherIcon: ImageView = view.iv_weather_icon
    }
}