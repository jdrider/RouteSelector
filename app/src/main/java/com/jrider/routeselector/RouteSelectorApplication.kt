package com.jrider.routeselector

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.jrider.routeselector.dagger.ApplicationComponent
import com.jrider.routeselector.dagger.DaggerApplicationComponent
import com.pacoworks.rxpaper.RxPaperBook

class RouteSelectorApplication : Application() {

    companion object{
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDependencies()

        appComponent = DaggerApplicationComponent.builder().build()
    }

    private fun initDependencies(){
        AndroidThreeTen.init(this)

        RxPaperBook.init(this)
    }
}