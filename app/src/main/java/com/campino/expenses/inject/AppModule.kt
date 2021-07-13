package com.campino.expenses.inject

import android.app.Application
import com.campino.expenses.datasource.TransactionDataSourceImpl
import com.campino.expenses.domine.TransactionsRepository
import com.campino.expenses.model.AppDatabase
import com.campino.expenses.repository.TransactionDataSource
import com.campino.expenses.repository.TransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun providesTransactionsRepository(dataSource: TransactionDataSource): TransactionsRepository {
        return TransactionRepositoryImpl(dataSource)
    }
    @Singleton
    @Provides
    fun providesTransactionDataSource(db: AppDatabase): TransactionDataSource {
        return TransactionDataSourceImpl(db.transactionDao())
    }
    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): AppDatabase {
        return AppDatabaseProvider.providesAppDatabase(application)
    }
}
