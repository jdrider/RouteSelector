package com.jrider.routeselector.mvp

interface BasePresenter<in T: BaseView> {

    fun attachView(view: T)

    fun detachView()
}