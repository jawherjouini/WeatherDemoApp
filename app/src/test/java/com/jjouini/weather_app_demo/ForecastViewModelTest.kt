package com.jjouini.weather_app_demo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jjouini.weather_app_demo.core.viewmodel.ForecastViewModel
import com.jjouini.weather_app_demo.framework.repository.ForecastRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import java.util.concurrent.TimeUnit


@RunWith(JUnit4::class)
class ForecastViewModelTest {
    private lateinit var viewModel: ForecastViewModel
    private lateinit var repository: ForecastRepository
    private val frequency = 5

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = ForecastRepository()
        viewModel = ForecastViewModel()
    }

    @Test
    fun getForecastTest() {
        viewModel.getStatusList("chicago", frequency)
        viewModel.statusListLiveData.observeForever { list ->
            assertEquals(list.isNotEmpty(), true)
            var lastDate = Date()
            var isFrequencyCorrect = true
            list.forEach { item ->
                val diffInMs: Long = (item.dtTxt?.time!!) - lastDate.time
                val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMs)
                if (diffInHours < frequency) {
                    isFrequencyCorrect = false
                }
                lastDate = item.dtTxt!!
            }
            assertEquals(isFrequencyCorrect, true)
        }
    }
}
