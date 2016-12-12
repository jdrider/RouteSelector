package com.jrider.routeselector

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.jrider.routeselector.dagger.ApplicationComponent
import com.jrider.routeselector.dagger.DaggerApplicationComponent

class RouteSelectorApplication : Application() {

    companion object{
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        appComponent = DaggerApplicationComponent.builder().build()
    }
}