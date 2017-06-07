package com.jrider.routeselector.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class UUIDTypeAdapter{

    @FromJson
    fun fromJson(uuidString: String) : UUID{
        return UUID.fromString(uuidString)
    }

    @ToJson
    fun toJson(uuid: UUID): String{
        return uuid.toString()
    }

}
