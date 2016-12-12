package com.jrider.routeselector.features.routes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jrider.routeselector.R

class EditRouteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_route)

        val editRouteFragment = EditRouteFragment()

        supportFragmentManager.beginTransaction().replace(R.id.activity_add_route, editRouteFragment).commit()
    }
}
