package com.jrider.routeselector.api

import com.jrider.routeselector.BuildConfig
import com.jrider.routeselector.features.routes.Route
import java.util.*

class DirectionsRequest(val route: Route) {

    val ORIGIN_KEY = "origin"
    val DESTINATION_KEY = "destination"
    val API_KEY_KEY = "key"
    val DEPARTURE_TIME_KEY = "departure_time"

    fun generateRequestMap(): Map<String, String> {

        val requestQueryMap = LinkedHashMap<String, String>()

        requestQueryMap.put(ORIGIN_KEY, generateOriginString())
        requestQueryMap.put(DESTINATION_KEY, generateDestinationString())

        //TODO Update to dynamic departure time
        requestQueryMap.put(DEPARTURE_TIME_KEY, "now")

        requestQueryMap.put(API_KEY_KEY, BuildConfig.DIRECTIONS_API_KEY)

        return requestQueryMap
    }

    private fun generateOriginString(): String {
        return "${route.startPoint.latitude},${route.startPoint.longitude}"
    }

    private fun generateDestinationString(): String {
        return "${route.endPoint.latitude},${route.endPoint.longitude}"
    }
}