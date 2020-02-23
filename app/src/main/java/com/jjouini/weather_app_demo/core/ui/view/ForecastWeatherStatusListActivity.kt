package com.jjouini.weather_app_demo.core.ui.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jjouini.weather_app_demo.R
import com.jjouini.weather_app_demo.core.model.WeatherStatus
import com.jjouini.weather_app_demo.core.ui.adapter.ForecastRecyclerViewAdapter
import com.jjouini.weather_app_demo.core.viewmodel.ForecastViewModel
import com.jjouini.weather_app_demo.core.viewmodel.factory.ForecastViewModelFactory
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ForecastWeatherStatusDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ForecastWeatherStatusListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var doubleBackToExitPressedOnce: Boolean = false
    private lateinit var forecastViewModel: ForecastViewModel
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    companion object {
        const val LOCATION = "paris"
        const val FREQUENCY = 8
        const val DEVELOPER_PHONE_NUMBER = "<PUT_A_PHONE_NUMBER_HERE>"
        const val APP_BUNDLE = "com.jjouini.weather_app_demo.bundle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        fetchData()

        setSupportActionBar(toolbar)
        toolbar.title = title

        btn_send_sms.setOnClickListener {
            val uri = Uri.parse(
                String.format(
                    resources.getString(R.string.send_sms_phone_number),
                    DEVELOPER_PHONE_NUMBER
                )
            )
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", resources.getString(R.string.send_sms_body))
            startActivity(intent)
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

    }

    private fun fetchData() {
        val viewModelFactory = ForecastViewModelFactory()
        forecastViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ForecastViewModel::class.java)
        progressBar.visibility = View.VISIBLE
        forecastViewModel.getStatusList(LOCATION, FREQUENCY)
        forecastViewModel.statusListLiveData.observe(this, Observer { mutableList ->
            setupRecyclerView(item_list, mutableList)
            progressBar.visibility = View.GONE
        })
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        items: MutableList<WeatherStatus>
    ) {
        recyclerView.adapter =
            ForecastRecyclerViewAdapter(
                this,
                items,
                twoPane
            )
    }

    override fun onBackPressed() {
        coordinatorLayout = findViewById(R.id.cl_main)
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true
            Snackbar.make(
                coordinatorLayout,
                resources.getString(R.string.leave_application_message),
                Snackbar.LENGTH_LONG
            ).setAction(
                resources.getString(R.string.user_approval_message)
            ) {
                finish()
            }.setActionTextColor(Color.YELLOW)
                .show()

            mHandler = Handler()
            mRunnable = Runnable { doubleBackToExitPressedOnce = false }
            mHandler.postDelayed(mRunnable, 2000)
        }
    }

}