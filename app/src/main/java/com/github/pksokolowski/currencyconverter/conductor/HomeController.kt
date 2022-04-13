package com.github.pksokolowski.currencyconverter.conductor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bluelinelabs.conductor.Controller
import com.github.pksokolowski.currencyconverter.R

class HomeController : Controller() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.controller_home, container, false)
        (view.findViewById(R.id.tv_title) as TextView).text = "Hello World"
        return view
    }
}