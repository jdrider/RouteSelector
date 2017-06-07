package com.jrider.routeselector.dagger

import com.jrider.routeselector.features.routes.EditRouteFragment
import com.jrider.routeselector.features.routes.RouteListFragment
import com.jrider.routeselector.job.DirectionsRequestJob
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(StorageModule::class, PresenterModule::class, ApiModule::class))
interface ApplicationComponent {

    fun inject(editRouteFragment: EditRouteFragment)

    fun inject(routeListFragment: RouteListFragment)

    fun inject(directionsRequestJob: DirectionsRequestJob)
}