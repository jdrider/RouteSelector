package com.jrider.routeselector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.jrider.routeselector.features.routes.EditRouteActivity
import com.jrider.routeselector.features.routes.RouteContract
import com.jrider.routeselector.features.routes.RouteListFragment
import com.jrider.routeselector.util.DeviceUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val PLAY_SERVICES_DIALOG_REQUEST_CODE = 67
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_btn_add_route.setOnClickListener {
            val addRouteIntent = EditRouteActivity.startIntent(this, RouteContract.NEW_ROUTE_ID)

            startActivity(addRouteIntent)
        }

        val routeListFragment = RouteListFragment.newInstance()

        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, routeListFragment).commit()
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are *not* resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * [.onResumeFragments].
     */
    override fun onResume() {
        super.onResume()

        if (!DeviceUtils().isEmulator()) {
            checkForGooglePlayServices()
        }
    }

    private fun checkForGooglePlayServices() {

        val apiAvailability = GoogleApiAvailability.getInstance()

        val apiStatus = apiAvailability.isGooglePlayServicesAvailable(this)

        if (apiStatus != ConnectionResult.SUCCESS) {

            if (apiAvailability.isUserResolvableError(apiStatus)) {
                apiAvailability.getErrorDialog(this, apiStatus, PLAY_SERVICES_DIALOG_REQUEST_CODE).show()
            }
        }
    }
}
