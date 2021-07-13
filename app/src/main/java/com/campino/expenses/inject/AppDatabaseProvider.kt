package com.campino.expenses.inject

import android.app.Application
import androidx.room.Room
import com.campino.expenses.model.AppDatabase

object AppDatabaseProvider {
    const val DATA_BASE_NAME = "data-model"
    const val DATA_BASE_VERSION=1

    fun providesAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DATA_BASE_NAME)
            .build()
    }
}