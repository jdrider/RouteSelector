package com.jrider.routeselector.features.routes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jrider.routeselector.R

class EditRouteActivity() : AppCompatActivity() {

    companion object{

        private const val ROUTE_ID_KEY: String = "EditRouteActivity.RouteId"

        fun startIntent(context: Context, routeId: Int) : Intent {

            val startIntent = Intent(context, EditRouteActivity::class.java)

            startIntent.putExtra(ROUTE_ID_KEY, routeId)

            return startIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_route)

        val routeId = intent.getIntExtra(ROUTE_ID_KEY, RouteContract.NEW_ROUTE_ID)

        val editRouteFragment = EditRouteFragment.newInstance(routeId)

        supportFragmentManager.beginTransaction().replace(R.id.activity_add_route, editRouteFragment).commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleResId = if(routeId == RouteContract.NEW_ROUTE_ID){
            R.string.add_route_add_route_title
        }
        else{
            R.string.add_route_edit_route_title
        }

        supportActionBar?.setTitle(titleResId)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
