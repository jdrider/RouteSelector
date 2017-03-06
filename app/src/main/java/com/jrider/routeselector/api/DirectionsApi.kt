package com.jrider.routeselector.api

import com.jrider.routeselector.api.model.DirectionsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * https://developers.google.com/maps/documentation/directions/intro
 */
interface DirectionsApi {

    @GET("/maps/api/directions/json")
    fun routeList(@QueryMap routeParameters: Map<String, String>): Call<DirectionsResponse>
}