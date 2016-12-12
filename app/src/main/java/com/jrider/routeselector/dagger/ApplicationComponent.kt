package com.jrider.routeselector.dagger

import com.jrider.routeselector.features.routes.EditRouteFragment
import dagger.Component

@Component
interface ApplicationComponent {

    fun inject(editRouteFragment: EditRouteFragment)
}