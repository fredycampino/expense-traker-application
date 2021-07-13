package com.campino.expenses.inject

import android.app.Application
import com.campino.expenses.view.dashboard.DashBoardComponent
import com.campino.expenses.view.transaction.NewTransactionComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
    //Sub components
    fun  getDashBoardComponent(): DashBoardComponent
    fun  getTransactionComponent(): NewTransactionComponent

    @Component.Builder
    interface Builder{
        fun build():AppComponent

        /** Provide the application instance to AppModule */
        @BindsInstance
        fun application(application: Application):Builder
    }

    fun inject(daggerApp: DaggerApp)
    fun inject(instanceProvides: InstanceProvides)
}