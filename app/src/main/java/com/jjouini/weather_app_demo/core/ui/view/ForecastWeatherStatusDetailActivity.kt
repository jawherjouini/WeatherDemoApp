package com.jjouini.weather_app_demo.core.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jjouini.weather_app_demo.R
import com.jjouini.weather_app_demo.core.model.WeatherStatus
import com.jjouini.weather_app_demo.core.ui.view.ForecastWeatherStatusListActivity.Companion.APP_BUNDLE
import com.jjouini.weather_app_demo.framework.api.OpenWeatherApiService
import com.jjouini.weather_app_demo.framework.util.ForecastUtils
import com.jjouini.weather_app_demo.framework.util.Units.DATETIME_FORMAT
import kotlinx.android.synthetic.main.activity_item_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ForecastWeatherStatusListActivity].
 */
class ForecastWeatherStatusDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        // http://developer.android.com/guide/components/fragments.html
        val bundle = intent.getBundleExtra(APP_BUNDLE)
        val item: WeatherStatus? =
            bundle?.getParcelable(ForecastWeatherStatusDetailFragment.ARG_LIST_ITEM)
        val dateFormatter = SimpleDateFormat(DATETIME_FORMAT, Locale.getDefault())
        supportActionBar?.title = dateFormatter.format(item?.dtTxt!!)
        bg_weather_img?.setBackgroundResource(ForecastUtils.getBackgroundDrawable(item))
        btn_share.setOnClickListener {
            val jsonString = OpenWeatherApiService.gson.toJson(item)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, jsonString)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = ForecastWeatherStatusDetailFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(
                            ForecastWeatherStatusDetailFragment.ARG_LIST_ITEM, item
                        )
                    }
                }

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
//                navigateUpTo(Intent(this, ForecastWeatherStatusListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
