package com.jrider.routeselector.features.routes

import com.jrider.routeselector.mvp.BasePresenter
import com.jrider.routeselector.mvp.BaseView

interface RouteContract {

    companion object {
        const val NEW_ROUTE_ID = "00000000-0000-0000-0000-000000000000"
    }

    interface View : BaseView {

        fun setRouteDepartureTime(routeTime: String)

        fun setRouteNickname(routeNickname: String)

        fun setStartpoint()

        fun setEndpoint()

        fun setNotificationTime(notificationTime: Int)

        fun routeSaved()
    }

    interface Presenter : BasePresenter<View> {

        fun saveRoute(name: String,
                      startPoint: String,
                      endPoint: String,
                      notificationTime: Int)

        fun setRoute(routeId: String = NEW_ROUTE_ID)

        fun routeDepartureTimeUpdated(hour: Int, minute: Int)

        fun departureTimeHour(): Int

        fun departureTimeMinute(): Int

        fun allRoutes(): List<Route>
    }
}