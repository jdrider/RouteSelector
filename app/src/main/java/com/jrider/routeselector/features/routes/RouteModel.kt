package com.jrider.routeselector.features.routes

import com.jrider.routeselector.job.DirectionsRequestJob
import com.pacoworks.rxpaper.RxPaperBook
import rx.Completable
import rx.Observable
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

    fun saveRoute(departureTimeHours: Int, departureTimeMinutes: Int) {

        val minutesFromMidnight = (departureTimeHours * MINUTES_IN_HOUR) + departureTimeMinutes

        currentRoute = currentRoute.copy(departureTime = minutesFromMidnight)

        routeBook.write(currentRoute.id.toString(), currentRoute).subscribe()

        DirectionsRequestJob.scheduleJob(currentRoute, true)
    }

    fun updateRouteNotificationTime(notificationTime: Int): Completable {
        currentRoute = currentRoute.copy(notificationTime = notificationTime)

        return routeBook.write(currentRoute.id.toString(), currentRoute)
    }

    fun updateRouteName(name: String): Completable {
        currentRoute = currentRoute.copy(name = name)

        return routeBook.write(currentRoute.id.toString(), currentRoute)
    }

    fun updateRouteStartPoint(startPointName: String, startLatitude: Double, startLongitude: Double): Completable {

        val newStartPoint = RoutePoint(name = startPointName, latitude = startLatitude, longitude = startLongitude)

        currentRoute = currentRoute.copy(startPoint = newStartPoint)

        return routeBook.write(currentRoute.id.toString(), currentRoute)
    }

    fun updateRouteEndpoint(endPointName: String, endLatitude: Double, endLongitude: Double): Completable {
        val newEndPoint = RoutePoint(name = endPointName, latitude = endLatitude, longitude = endLongitude)

        currentRoute = currentRoute.copy(endPoint = newEndPoint)

        return routeBook.write(currentRoute.id.toString(), currentRoute)
    }

    fun currentRouteDepartureTimeHour(): Int {
        return currentRoute.departureTime / RoutePresenter.MINUTES_IN_HOUR
    }

    fun currentRouteDepartureTimeMinute(): Int {
        val hoursSinceMidnight = currentRoute.departureTime / RoutePresenter.MINUTES_IN_HOUR

        val minutesPastHour = currentRoute.departureTime - (hoursSinceMidnight * RoutePresenter.MINUTES_IN_HOUR)

        return minutesPastHour
    }

    fun allRoutes(): List<Route> {

        return routeBook.keys()
                .flatMapObservable { Observable.from(it) }
                .flatMap { routeBook.read<Route>(it).toObservable() }
                .toList()
                .toBlocking()
                .first()
    }
}