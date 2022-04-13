package com.github.pksokolowski.currencyconverter.conductor

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.pksokolowski.currencyconverter.R

class HomeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    init {
        val view: View = inflate(context, R.layout.view_home, this)


    }

}