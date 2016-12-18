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
                .subscribe({ route -> routeView.setRouteTime(formatTime(route.departureTime)) },
                           Throwable::printStackTrace)
    }

    override fun saveRoute(name: String,
                           startPoint: String,
                           endPoint: String,
                           notificationTime: Int) {

        routeModel.saveRoute(name, startPoint, endPoint, notificationTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ routeView.routeSaved() }, Throwable::printStackTrace)
    }

    override fun routeDepartureTimeUpdated(hour: Int, minute: Int) {

        routeModel.saveRoute(hour, minute)

        val formattedTimeString = formatTime(hour, minute)

        routeView.setRouteTime(formattedTimeString)
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

}
