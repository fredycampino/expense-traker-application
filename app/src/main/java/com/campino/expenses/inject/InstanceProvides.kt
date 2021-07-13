package com.campino.expenses.inject


import android.app.Application
import com.campino.expenses.R
import com.campino.expenses.domine.TransactionsRepository
import com.campino.expenses.model.AppDatabase
import javax.inject.Inject

class InstanceProvides {

    @Inject
    lateinit var repository: TransactionsRepository

    @Inject
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var application: Application

    fun inject(application: DaggerApp): InstanceProvides {
        //TODO add a runtime exception if it is a release
        application.appComponent.inject(this)
        return this
    }

    fun deleteAppDatabase() {
        application.deleteDatabase(AppDatabaseProvider.DATA_BASE_NAME)
    }

    fun getString(res: Int): String {
        return application.getString(R.string.label_cash)
    }
}