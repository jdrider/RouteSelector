package com.jrider.routeselector.dagger

import com.jrider.routeselector.features.routes.EditRouteFragment
import dagger.Component

@Component(modules = arrayOf(StorageModule::class, PresenterModule::class))
interface ApplicationComponent {

    fun inject(editRouteFragment: EditRouteFragment)
}