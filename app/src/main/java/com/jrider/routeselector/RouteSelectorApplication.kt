package com.jrider.routeselector

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class RouteSelectorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this);
    }
}