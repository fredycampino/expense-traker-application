package com.campino.expenses.view.dashboard

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.campino.expenses.domine.TransactionEntity
import com.campino.expenses.domine.TransactionsRepository
import com.campino.expenses.domine.toList
import com.campino.expenses.view.BaseViewModel
import com.campino.expenses.view.observe

class DashBoardViewModel(private val transactionRepository: TransactionsRepository) :
    BaseViewModel() {
    private val accountSize: Int = 10
    private val onTransactions = MutableLiveData<List<TransactionEntity>>()
    fun onTransactions(owner: LifecycleOwner, observer: (List<TransactionEntity>) -> Unit) {
        onTransactions.observe(owner, observer)
    }

    fun fetch() {
        launchWithProgress {
            val result = transactionRepository.getTransactions()
            onTransactions.value = result.toList(accountSize)
        }
    }

    fun delete(entity: TransactionEntity) {
        launchWithProgress {
            transactionRepository.delete(entity)
            val result = transactionRepository.getTransactions()
            onTransactions.value = result.toList(accountSize)
        }
    }
}