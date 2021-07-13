package com.campino.expenses.view.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.campino.expenses.domine.TransactionsRepository
import com.campino.expenses.inject.ActivityScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [DashBoardModule::class])
interface DashBoardComponent {
    fun inject(activity: DashBoardActivity)
}

@Module
class DashBoardModule {
    @Provides
    fun providesDashBoardFactory(repository: TransactionsRepository): DashBoardFactory {
        return DashBoardFactory(repository)
    }
}

class DashBoardFactory(
    val transactionsRepository: TransactionsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashBoardViewModel(transactionsRepository) as T
    }
}
