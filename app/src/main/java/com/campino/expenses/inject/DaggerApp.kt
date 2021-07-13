package com.campino.expenses.inject

import android.app.Application
import androidx.lifecycle.LifecycleObserver

class DaggerApp : Application(), LifecycleObserver {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}