package com.jrider.routeselector.features.routes

import com.jrider.routeselector.mvp.BasePresenter
import com.jrider.routeselector.mvp.BaseView

interface RouteContract {

    interface View : BaseView {

        fun setRouteTime(routeTime: String)
    }

    interface Presenter : BasePresenter<View> {

        fun saveRoute()
    }
}