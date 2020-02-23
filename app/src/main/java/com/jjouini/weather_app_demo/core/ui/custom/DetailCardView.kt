package com.jjouini.weather_app_demo.core.ui.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.jjouini.weather_app_demo.R

class DetailCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : CardView(context, attrs, defStyleRes) {
    private var tvFirstRow: TextView
    private var tvSecondRow: TextView
    private var tvThirdRow: TextView
    private var tvDetailTitle: TextView

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.detail_card_view, this, true)
        tvFirstRow = findViewById(R.id.tv_first_row)
        tvSecondRow = findViewById(R.id.tv_second_row)
        tvThirdRow = findViewById(R.id.tv_third_row)
        tvDetailTitle = findViewById(R.id.tv_detail_title)
    }

    fun setTitle(title: String) {
        tvDetailTitle.text = title
    }

    fun setBlueTitle() {
        tvDetailTitle.setTextColor(Color.BLUE)
    }

    fun setRedTitle() {
        tvDetailTitle.setTextColor(Color.RED)
    }

    fun setFirstRow(content: String, unit: String, icon: Int) {
        tvFirstRow.visibility = View.VISIBLE
        tvFirstRow.text = String.format(unit, content)
        tvFirstRow.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(icon, null),
            null,
            null,
            null
        )
    }

    fun setSecondRow(content: String, unit: String, icon: Int) {
        tvSecondRow.visibility = View.VISIBLE
        tvSecondRow.text = String.format(unit, content)
        tvSecondRow.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(icon, null),
            null,
            null,
            null
        )
    }

    fun setThirdRow(content: String, unit: String, icon: Int) {
        tvThirdRow.visibility = View.VISIBLE
        tvThirdRow.text = String.format(unit, content)
        tvThirdRow.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(icon, null),
            null,
            null,
            null
        )
    }

}