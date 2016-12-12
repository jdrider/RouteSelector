package com.jrider.routeselector.features.routes

import com.jrider.routeselector.mvp.BasePresenter
import com.jrider.routeselector.mvp.BaseView

interface RouteContract {

    companion object{
        const val NEW_ROUTE_ID = -1
    }

    interface View : BaseView {

        fun setRouteTime(routeTime: String)
    }

    interface Presenter : BasePresenter<View> {

        fun saveRoute()

        fun setRoute(routeId: Int = NEW_ROUTE_ID)
    }
}