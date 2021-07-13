package com.campino.expenses.view.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.campino.expenses.domine.TransactionsRepository
import com.campino.expenses.inject.ActivityScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [NewTransactionModule::class])
interface NewTransactionComponent {
    fun inject(activity: NewTransactionActivity)
}

@Module
class NewTransactionModule {
    @Provides
    fun providesTransactionsRepository(repository: TransactionsRepository): NewTransactionFactory {
        return NewTransactionFactory(repository)
    }
}

class NewTransactionFactory(
    val transactionsRepository: TransactionsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewTransactionViewModel(transactionsRepository) as T
    }
}
