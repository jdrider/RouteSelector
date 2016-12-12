package com.jrider.routeselector

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jrider.routeselector.features.routes.EditRouteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_btn_add_route.setOnClickListener { view ->
            val addRouteIntent = Intent(this, EditRouteActivity::class.java)

            startActivity(addRouteIntent)
        }
    }
}
