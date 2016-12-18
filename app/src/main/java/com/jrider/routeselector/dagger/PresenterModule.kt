package com.jrider.routeselector.dagger

import com.jrider.routeselector.features.routes.RouteContract
import com.jrider.routeselector.features.routes.RouteModel
import com.jrider.routeselector.features.routes.RoutePresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun providesRouteContractPresenter(routeModel: RouteModel): RouteContract.Presenter {
        return RoutePresenter(routeModel)
    }
}