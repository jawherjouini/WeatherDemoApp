package com.jjouini.weather_app_demo.core.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jjouini.weather_app_demo.core.viewmodel.ForecastViewModel

class ForecastViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForecastViewModel() as T
    }
}