package com.campino.expenses.view.transaction

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.campino.expenses.domine.*
import com.campino.expenses.view.BaseViewModel
import com.campino.expenses.view.observe
import java.util.*

class NewTransactionViewModel(private val transactionRepository: TransactionsRepository) :
    BaseViewModel() {

    private val onStatus = MutableLiveData<FormStatus>()
    fun onStatus(owner: LifecycleOwner, observer: (FormStatus) -> Unit) {
        onStatus.observe(owner, observer)
    }

    fun create(form: FormTransaction) {
        val account = form.accountType ?: return
        val category = form.category ?: return
        val date = Date(System.currentTimeMillis())
        if (form.value.isEmpty()) return
        val value = form.value

        val transaction = if (form.income)
            IncomeEntity(
                account = account,
                category = category as IncomeCategory,
                date = date,
                value = value.toFloat()
            )
        else
            ExpensesEntity(
                account = account,
                category = category as ExpenseCategory,
                date = date,
                value = value.toFloat()
            )

        launchWithProgress {
            transactionRepository.postTransactions(transaction)
            onStatus.value = FormStatus.DONE
        }
    }

    fun formChange(form: FormTransaction) {
        onStatus.value = getFormStatus(form)
    }

    private fun getFormStatus(form: FormTransaction): FormStatus {
        return if (form.accountType != null && form.category != null && form.value.isNotEmpty())
            FormStatus.TODO
        else FormStatus.INCOMPLETE
    }
}


data class FormTransaction(
    val income: Boolean,
    val accountType: AccountType?,
    val category: Enum<*>?,
    val value: String
)

enum class FormStatus {
    INCOMPLETE,
    TODO,
    DONE
}