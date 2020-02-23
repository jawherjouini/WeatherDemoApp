package com.jjouini.weather_app_demo.ui.listActivity

import android.content.ComponentName
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jjouini.weather_app_demo.R
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusDetailActivity
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ForecastListActivityTest {

    private lateinit var idlingResource: RecyclerViewIdlingResource

    companion object {
        const val SPLASH_TIME: Long = 3000
    }


    @get:Rule
    var activityTestRule: IntentsTestRule<ForecastWeatherStatusListActivity> =
        IntentsTestRule(
            ForecastWeatherStatusListActivity::class.java
        )

    @Before
    fun setUp() {
        idlingResource =
            RecyclerViewIdlingResource(SPLASH_TIME) // mActivityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun testSendSMSButton() {
        onView(withId(R.id.btn_send_sms)).perform(click())
    }

    private fun onViewList(): ViewInteraction? {
        return onView(withId(R.id.item_list))
    }

    @Test
    fun isListVisible() {
        onViewList()?.check(matches(isDisplayed()))
    }

    @Test
    fun launchActivityTest() {
        onView(withId(R.id.item_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        intended(
            hasComponent(
                ComponentName(
                    activityTestRule.activity,
                    ForecastWeatherStatusDetailActivity::class.java
                )
            )
        )
    }
}
