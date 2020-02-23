package com.jjouini.weather_app_demo.ui.detailActivity

import android.content.Intent
import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.gson.reflect.TypeToken
import com.jjouini.weather_app_demo.R
import com.jjouini.weather_app_demo.core.model.WeatherStatus
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusDetailActivity
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusDetailFragment
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusListActivity
import com.jjouini.weather_app_demo.framework.api.OpenWeatherApiService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type


@RunWith(AndroidJUnit4::class)
@LargeTest
class ForecastDetailActivityTest {

    @get:Rule
    val activityRule =
        ActivityTestRule(ForecastWeatherStatusDetailActivity::class.java, false, false)

    @Before
    fun configActivity() {
        val classLoader: ClassLoader = ForecastDetailActivityTest::class.java.classLoader!!
        val inputStream = classLoader.getResourceAsStream("mock.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val type: Type = object : TypeToken<WeatherStatus?>() {}.type
        val mockedObject =
            OpenWeatherApiService.gson.fromJson(bufferedReader, type) as WeatherStatus
        val i = Intent()
        val bundle = Bundle()
        bundle.putParcelable(ForecastWeatherStatusDetailFragment.ARG_LIST_ITEM, mockedObject)
        i.putExtra(ForecastWeatherStatusListActivity.APP_BUNDLE, bundle)
        activityRule.launchActivity(i)
    }

    @Test
    fun testShareButton() {
        onView(withId(R.id.btn_share)).perform(click())
    }

    private fun onViewList(): ViewInteraction? {
        return onView(withId(R.id.root_layout))
    }

    @Test
    fun isListVisible() {
        onViewList()?.check(matches(isDisplayed()))
    }

}
