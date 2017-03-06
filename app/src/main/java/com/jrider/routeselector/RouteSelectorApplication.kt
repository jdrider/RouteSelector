package com.jrider.routeselector

import android.app.Application
import com.evernote.android.job.JobManager
import com.jrider.routeselector.dagger.ApplicationComponent
import com.jrider.routeselector.dagger.DaggerApplicationComponent
import com.jrider.routeselector.job.RouteJobCreator
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

        JobManager.create(this).addJobCreator(RouteJobCreator())
    }
}