package com.jrider.routeselector.features.routes

import java.util.*

data class RoutePoint(val id: UUID = UUID.randomUUID(),
                      val name: String = "",
                      val longitude: Double = 0.0,
                      val latitude: Double = 0.0)