package com.jrider.routeselector.features.routes

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class RoutePresenter @Inject constructor() : RouteContract.Presenter{

    private lateinit var routeView: RouteContract.View

    private val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun attachView(view: RouteContract.View) {
        this.routeView = view
    }

    override fun detachView() {
        subscriptions.unsubscribe()
    }

    override fun setRoute(routeId: Int){

        if(routeId >= 0){
            //TODO Get Existing route
        }
        else {
            //New route
            routeView.setRouteTime(formatTime(LocalDateTime.now()))
        }
    }

    override fun saveRoute() {
        throw UnsupportedOperationException(
                "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun formatTime(time: LocalDateTime) : String{
        return DateTimeFormatter.ofPattern("h:mm a").format(time)
    }

}
