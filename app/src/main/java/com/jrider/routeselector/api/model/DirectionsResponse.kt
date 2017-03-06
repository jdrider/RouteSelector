package com.jrider.routeselector.api.model

data class DirectionsResponse(
    val status: String,
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<ApiRoute>
)