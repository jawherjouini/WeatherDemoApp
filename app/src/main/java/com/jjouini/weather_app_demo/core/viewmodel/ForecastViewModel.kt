package com.jjouini.weather_app_demo.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjouini.weather_app_demo.core.model.WeatherStatus
import com.jjouini.weather_app_demo.framework.repository.ForecastRepository
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ForecastViewModel : ViewModel() {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)
    private val forecastRepository: ForecastRepository =
        ForecastRepository()

    val statusListLiveData = MutableLiveData<MutableList<WeatherStatus>>()

    fun getStatusList(cityName: String, frequency: Int) {
        scope.launch {
            val list = forecastRepository.getForecast(cityName)
            val result = mutableListOf<WeatherStatus>()

            if (!list.isNullOrEmpty()) {
                // Keep only 8 hours refresh
                var lastExpectedTime = getExpectedDelayedTime(frequency, list[0].dtTxt!!)
                result.add(list[0])
                list.forEach { item ->
                    val date = item.dtTxt!!
                    if (date >= lastExpectedTime) {
                        result.add(item)
                        lastExpectedTime = getExpectedDelayedTime(frequency, date)
                    }
                }
            }
            statusListLiveData.postValue(result)
        }
    }

    private fun getExpectedDelayedTime(frequency: Int, date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, frequency)
        return calendar.time
    }

    override fun onCleared() {
        super.onCleared()
        cancelRequests()
        parentJob.cancel()
    }

    private fun cancelRequests() = coroutineContext.cancel()
}