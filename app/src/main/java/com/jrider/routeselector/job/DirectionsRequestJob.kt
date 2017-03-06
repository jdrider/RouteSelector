package com.jrider.routeselector.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import com.jrider.routeselector.features.routes.Route
import com.squareup.moshi.Moshi
import kotlinx.coroutines.experimental.runBlocking
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class DirectionsRequestJob : Job() {

    companion object {
        const val TAG = "DirectionsRequestJob"

        private const val ROUTE_KEY = "RouteForJob"

        fun scheduleJob(scheduledRoute: Route, updateCurrent: Boolean) {

            val directionsJobExtras = jobExtraValues(scheduledRoute)

            val scheduleWindowStart = jobScheduleWindowStart(scheduledRoute.departureTime, scheduledRoute.notificationTime)

            val scheduleWindowEnd = jobScheduleWindowEnd(scheduleWindowStart, scheduledRoute.notificationTime)

            JobRequest.Builder(TAG)
                    .setExtras(directionsJobExtras)
                    .setUpdateCurrent(updateCurrent)
                    .setPersisted(true)
                    .setExecutionWindow(scheduleWindowStart, scheduleWindowEnd)
                    .build()
                    .schedule()
        }

        private fun jobExtraValues(routeJob: Route): PersistableBundleCompat {

            val moshi = Moshi.Builder().build()

            val routeJsonAdapter = moshi.adapter(Route::class.java)

            val routeJsonString = routeJsonAdapter.toJson(routeJob)

            val jobExtras = PersistableBundleCompat()

            jobExtras.putString(ROUTE_KEY, routeJsonString)

            return jobExtras
        }

        private fun jobScheduleWindowStart(departureTime: Int, notificationTime: Int): Long {

            val systemCalendar = Calendar.getInstance()

            val currentHour = systemCalendar.get(Calendar.HOUR_OF_DAY)

            val departureOffset = departureTime - notificationTime

            val windowStartMs =
                    TimeUnit.MINUTES.toMillis(60 - departureOffset.toLong())
            +TimeUnit.HOURS.toMillis((24 - currentHour.toLong()) % 24)

            return windowStartMs
        }

        private fun jobScheduleWindowEnd(windowStartMs: Long, notificationTime: Int): Long {
            return windowStartMs + TimeUnit.MINUTES.toMillis(notificationTime.toLong())
        }
    }

    override fun onRunJob(params: Params): Result {

        Timber.i("Starting Directions Job")

        val routeJson = params.extras.getString(DirectionsRequestJob.ROUTE_KEY, "")

        val routeForJob = createRouteFromJson(routeJson)

        try {

            Timber.i("Ending Directions Job")

            return Result.SUCCESS
        } finally {

            Timber.w("Directions Job ended in error")

            scheduleJob(routeForJob, false)
        }
    }

    private fun requestRouteDirections(currentRoute: Route) = runBlocking {

        //        val directionsRequest = DirectionsRequest(currentRoute)
//
//        val responseJson = directionsApi.routeList(directionsRequest.generateRequestMap()).await()
//
//        Timber.d(responseJson.toString())
    }

    private fun createRouteFromJson(routeJson: String): Route {

        val moshi = Moshi.Builder().build()

        val routeJsonAdapter = moshi.adapter(Route::class.java)

        return routeJsonAdapter.fromJson(routeJson)
    }

}