package com.jrider.routeselector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jrider.routeselector.features.routes.EditRouteActivity
import com.jrider.routeselector.features.routes.RouteContract
import com.jrider.routeselector.features.routes.RouteListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_btn_add_route.setOnClickListener { view ->
            val addRouteIntent = EditRouteActivity.startIntent(this, RouteContract.NEW_ROUTE_ID)

            startActivity(addRouteIntent)
        }

        val routeListFragment = RouteListFragment.newInstance()

        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, routeListFragment).commit()
    }
}
