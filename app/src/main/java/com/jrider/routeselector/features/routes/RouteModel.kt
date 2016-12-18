package com.jrider.routeselector.features.routes

import com.pacoworks.rxpaper.RxPaperBook
import rx.Completable
import rx.Single
import java.util.*
import javax.inject.Inject

class RouteModel @Inject constructor() {

    @Inject
    lateinit var routeBook: RxPaperBook

    private lateinit var currentRoute: Route

    fun setRoute(routeId: UUID): Single<Route> {

        if(routeId == UUID.fromString(RouteContract.NEW_ROUTE_ID)){
            currentRoute = Route()

            return Single.just(currentRoute)
        }
        else {
            return routeBook.read<Route>(routeId.toString()).doOnSuccess { selectedRoute -> currentRoute = selectedRoute }
        }
    }

    fun saveRoute(): Completable {
        return routeBook.write(currentRoute.id.toString(), currentRoute)
    }

}