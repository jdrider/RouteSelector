package com.jrider.routeselector.api.model

data class ApiRoute(
        val summary: String,
        val legs: List<Leg>
)