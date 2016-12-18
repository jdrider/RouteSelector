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
                  departureTime: String,
                  notificationTime: Int): Completable {

        val departureMinutes = convertTimeToMinutesFromMidnight(departureTime)

        currentRoute = currentRoute.copy(name = name,
                                         startPoint = startPoint,
                                         endPoint = endPoint,
                                         departureTime = departureMinutes,
                                         notificationTime = notificationTime)

        return routeBook.write(currentRoute.id.toString(), currentRoute)
    }

    private fun convertTimeToMinutesFromMidnight(departureTime: String): Int {

        val timeComponents = departureTime.split(":")

        val hours = timeComponents[0].toInt()
        val minutes = timeComponents[1].toInt()

        return (hours * MINUTES_IN_HOUR) + minutes
    }

}