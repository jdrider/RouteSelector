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

        fun setStartpoint(startPointName: String)

        fun setEndpoint(endPointName: String)

        fun setNotificationTime(notificationTime: Int)
    }

    interface Presenter : BasePresenter<View> {

        fun updateRouteNotificationTime(notificationTime: Int)

        fun updateRouteName(name: String)

        fun updateRouteStartPoint(startPointName: String, startLatitude: Double, startLongitude: Double)

        fun updateRouteEndpoint(endPointName: String, endLatitude: Double, endLongitude: Double)

        fun saveRouteEnabledStatus(routeId: String, enabled: Boolean)

        fun setRoute(routeId: String = NEW_ROUTE_ID)

        fun routeDepartureTimeUpdated(hour: Int, minute: Int)

        fun departureTimeHour(): Int

        fun departureTimeMinute(): Int

        fun allRoutes(): List<Route>
    }
}