package com.jrider.routeselector.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class RouteJobCreator : JobCreator {

    override fun create(tag: String): Job? {

        return when (tag) {
            DirectionsRequestJob.TAG -> DirectionsRequestJob()
            else -> null
        }
    }
}