package com.jrider.routeselector

import android.app.Application
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
        RxPaperBook.init(this)
    }
}