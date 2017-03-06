package com.jrider.routeselector.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest

class DirectionsRequestJob : Job() {

    companion object{
        const val TAG = "DirectionsRequestJob"

        fun scheduleJob(){

            JobRequest.Builder(TAG)
                    .build()
                    .schedule()
        }
    }

    override fun onRunJob(params: Params?): Result {

        return Result.SUCCESS
    }
}