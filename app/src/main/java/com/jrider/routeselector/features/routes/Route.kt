package com.jrider.routeselector.features.routes

import java.util.*

data class Route(val id: UUID = UUID.randomUUID(),
                 val name: String = "",
                 val startPoint: RoutePoint = RoutePoint(),
                 val endPoint: RoutePoint = RoutePoint(),
                 val departureTime: Int = 0,
                 val notificationTime: Int = 0,
                 val enabled: Boolean = true)