package com.github.pksokolowski.currencyconverter.conductor

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor.attachRouter
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.Router.PopRootControllerMode
import com.bluelinelabs.conductor.RouterTransaction
import com.github.pksokolowski.currencyconverter.R


class ConductorActivity2 : Activity() {
    private var router: Router? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conductor2)
        val container = findViewById<View>(R.id.controller_container) as ViewGroup
        router = attachRouter(this, container, savedInstanceState)
            .setPopRootControllerMode(PopRootControllerMode.NEVER)
        if (!router!!.hasRootController()) {
            router!!.setRoot(RouterTransaction.with(HomeController()))
        }
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }
}