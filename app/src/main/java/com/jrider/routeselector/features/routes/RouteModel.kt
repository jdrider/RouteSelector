package com.jrider.routeselector.features.routes

import com.pacoworks.rxpaper.RxPaperBook
import rx.Completable
import rx.Single
import java.util.*
import javax.inject.Inject

class RouteModel @Inject constructor() {

    companion object {
        const val MINUTES_IN_HOUR = 60
    }

    @Inject
    lateinit var routeBook: RxPaperBook

    private lateinit var currentRoute: Route

    fun setRoute(routeId: UUID): Single<Route> {

        if (routeId == UUID.fromString(RouteContract.NEW_ROUTE_ID)) {
            currentRoute = Route()

            return Single.just(currentRoute)
        } else {
            return routeBook.read<Route>(
                    routeId.toString()).doOnSuccess { selectedRoute -> currentRoute = selectedRoute }
        }
    }

    fun saveRoute(name: String,
                  startPoint: String,
                  endPoint: String,
                  notificationTime: Int): Completable {

        currentRoute = currentRoute.copy(name = name,
                                         startPoint = startPoint,
                                         endPoint = endPoint,
                                         notificationTime = notificationTime)

        return routeBook.write(currentRoute.id.toString(), currentRoute)
    }

    fun saveRoute(departureTimeHours: Int, departureTimeMinutes: Int) {

        val minutesFromMidnight = (departureTimeHours * MINUTES_IN_HOUR) + departureTimeMinutes

        currentRoute = currentRoute.copy(departureTime = minutesFromMidnight)

        routeBook.write(currentRoute.id.toString(), currentRoute).subscribe()
    }

    fun currentRouteDepartureTimeHour(): Int {
        return currentRoute.departureTime / RoutePresenter.MINUTES_IN_HOUR
    }

    fun currentRouteDepartureTimeMinute(): Int{
        val hoursSinceMidnight = currentRoute.departureTime / RoutePresenter.MINUTES_IN_HOUR

        val minutesPastHour = currentRoute.departureTime - (hoursSinceMidnight * RoutePresenter.MINUTES_IN_HOUR)

        return minutesPastHour
    }
}