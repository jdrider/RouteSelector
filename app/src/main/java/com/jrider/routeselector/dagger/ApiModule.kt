package com.jrider.routeselector.dagger

import com.jrider.routeselector.BuildConfig
import com.jrider.routeselector.api.DirectionsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun providesDirectionsApi(retrofit: Retrofit): DirectionsApi {
        return retrofit.create(DirectionsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(): Retrofit {

        val retrofitClient = Retrofit.Builder()
                .baseUrl(BuildConfig.DIRECTIONS_API_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        return retrofitClient
    }
}