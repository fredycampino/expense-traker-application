package com.campino.expenses.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.campino.expenses.inject.AppDatabaseProvider

@Database(
    entities = [
        TransactionModel::class
    ],
    version = AppDatabaseProvider.DATA_BASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
}