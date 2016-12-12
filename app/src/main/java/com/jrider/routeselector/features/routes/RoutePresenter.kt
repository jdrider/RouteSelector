package com.jrider.routeselector.features.routes

import rx.subscriptions.CompositeSubscription


class RoutePresenter : RouteContract.Presenter{

    private lateinit var routeView: RouteContract.View

    private val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun attachView(view: RouteContract.View) {
        this.routeView = view
    }

    override fun detachView() {
        subscriptions.unsubscribe();
    }

    override fun saveRoute() {
        throw UnsupportedOperationException(
                "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
