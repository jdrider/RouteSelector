package com.jrider.routeselector

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jrider.routeselector.ui.routes.AddRouteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_add_route_btn.setOnClickListener { view ->
            val addRouteIntent = Intent(this, AddRouteActivity::class.java)

            startActivity(addRouteIntent)
        }
    }
}
