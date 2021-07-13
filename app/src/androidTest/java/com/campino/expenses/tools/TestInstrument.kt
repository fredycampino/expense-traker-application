package com.campino.expenses.tools

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.campino.expenses.inject.DaggerApp
import com.campino.expenses.inject.InstanceProvides


fun testInstrument(): InstanceProvides {
    val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    val instanceProvides = InstanceProvides()
    instanceProvides.inject(context as DaggerApp)
    return instanceProvides
}

inline fun <reified T : Activity> launchActivity(): ActivityScenario<T> {
    return ActivityScenario.launch(T::class.java)
}