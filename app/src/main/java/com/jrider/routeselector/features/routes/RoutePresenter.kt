package com.jrider.routeselector.features.routes

import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

class RoutePresenter @Inject constructor(private val routeModel: RouteModel) : RouteContract.Presenter {

    companion object {
        const val MINUTES_IN_HOUR = 60

        const val HOURS_IN_MORNING = 12

        const val AM_STRING = "AM"

        const val PM_STRING = "PM"
    }

    private lateinit var routeView: RouteContract.View

    private val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun attachView(view: RouteContract.View) {
        this.routeView = view
    }

    override fun detachView() {
        subscriptions.unsubscribe()
    }

    override fun setRoute(routeId: String) {

        routeModel.setRoute(UUID.fromString(routeId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (_, name, startPoint, endPoint, departureTime, notificationTime) ->
                    routeView.setRouteDepartureTime(formatTime(departureTime))
                    routeView.setRouteNickname(name)
                    routeView.setNotificationTime(notificationTime)
                    routeView.setStartpoint(startPoint.name)
                    routeView.setEndpoint(endPoint.name)
                },
                        Throwable::printStackTrace)
    }

    override fun saveRouteEnabledStatus(routeId: String, enabled: Boolean) {

        routeModel.routeBook.read<Route>(routeId)
                .flatMapCompletable { route ->
                    val updatedRoute = route.copy(enabled = enabled)
                    routeModel.routeBook.write(routeId, updatedRoute)
                }
                .subscribe()
    }

    override fun routeDepartureTimeUpdated(hour: Int, minute: Int) {

        routeModel.saveRoute(hour, minute)

        val formattedTimeString = formatTime(hour, minute)

        routeView.setRouteDepartureTime(formattedTimeString)
    }

    private fun formatTime(minutesSinceMidnight: Int): String {

        val hoursSinceMidnight = minutesSinceMidnight / MINUTES_IN_HOUR

        val minutesPastHour = minutesSinceMidnight - (hoursSinceMidnight * MINUTES_IN_HOUR)

        return formatTime(hoursSinceMidnight, minutesPastHour)
    }

    private fun formatTime(hoursSinceMidnight: Int, minutesPastHour: Int): String {
        val minutesPastHourString = if (minutesPastHour < 10) {
            "0$minutesPastHour"
        } else {
            minutesPastHour.toString()
        }

        val amPm = if (hoursSinceMidnight < HOURS_IN_MORNING) {
            AM_STRING
        } else {
            PM_STRING
        }

        return "$hoursSinceMidnight:$minutesPastHourString $amPm"
    }

    override fun departureTimeHour(): Int {
        return routeModel.currentRouteDepartureTimeHour()
    }

    override fun departureTimeMinute(): Int {
        return routeModel.currentRouteDepartureTimeMinute()
    }

    override fun allRoutes(): List<Route> {
        return routeModel.allRoutes()
    }

    override fun updateRouteNotificationTime(notificationTime: Int) {
        routeModel.updateRouteNotificationTime(notificationTime)
                .subscribe()
    }

    override fun updateRouteName(name: String) {
        routeModel.updateRouteName(name).subscribe()
    }

    override fun updateRouteStartPoint(startPointName: String, startLatitude: Double, startLongitude: Double) {
        routeModel.updateRouteStartPoint(startPointName, startLatitude, startLongitude).subscribe()
    }

    override fun updateRouteEndpoint(endPointName: String, endLatitude: Double, endLongitude: Double) {
        routeModel.updateRouteEndpoint(endPointName, endLatitude, endLongitude).subscribe()
    }
}
