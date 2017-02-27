package com.jrider.routeselector.api

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DirectionsApi {

    @GET("/maps/api/directions/json")
    fun routeList(@QueryMap routeParameters: Map<String, String>)
}