package com.jrider.routeselector.features.routes

import com.jrider.routeselector.mvp.BasePresenter
import com.jrider.routeselector.mvp.BaseView

interface RouteContract {

    companion object{
        const val NEW_ROUTE_ID = "00000000-0000-0000-0000-000000000000"
    }

    interface View : BaseView {

        fun setRouteTime(routeTime: String)

        fun routeSaved()
    }

    interface Presenter : BasePresenter<View> {

        fun saveRoute()

        fun setRoute(routeId: String = NEW_ROUTE_ID)
    }
}