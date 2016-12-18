package com.jrider.routeselector.dagger

import com.pacoworks.rxpaper.RxPaperBook
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @Provides
    fun providesRoutePaperBook(): RxPaperBook{
        return RxPaperBook.with("routes")
    }

}