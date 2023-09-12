package io.astronout.gamescatalogue.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.astronout.core.utils.ReleaseTree
import io.astronout.gamescatalogue.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

}