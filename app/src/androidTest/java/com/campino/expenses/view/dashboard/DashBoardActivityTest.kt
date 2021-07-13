package com.campino.expenses.view.dashboard

import com.campino.expenses.domine.*
import com.campino.expenses.inject.InstanceProvides
import com.campino.expenses.tools.launchActivity
import com.campino.expenses.tools.testInstrument
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class DashBoardActivityTest {

    private val instruments: InstanceProvides = testInstrument()

    private var income = IncomeEntity(
        uid = 0L,
        account = AccountType.BANK,
        category = IncomeCategory.SALARY,
        date = Calendar.getInstance().time,
        value = 3000f
    )
    private var expense = ExpensesEntity(
        uid = 0L,
        account = AccountType.BANK,
        category = ExpenseCategory.GROCERY,
        date = Calendar.getInstance().time,
        value = 3000f
    )


    @Before
    fun before()  = runBlocking{

        instruments.deleteAppDatabase()
        val repository = instruments.repository
        var time = Calendar.getInstance().time.time
        for ( i in 1..12) {
            repository.postTransactions(income.copy(
                account = AccountType.BANK,
                date = Date(time),
                value = 10f/i
            ))
            repository.postTransactions(income.copy(
                account = AccountType.CASH,
                date = Date(time),
                value = 10f/i
            ))
            repository.postTransactions(income.copy(
                account = AccountType.CREDIT,
                date = Date(time),
                value = 10f/i
            ))

            time -= 86400000L//One Day
        }

        time = Calendar.getInstance().time.time
        repository.postTransactions(expense.copy(
            account = AccountType.BANK,
            date = Date(time),
            value = 1.0f
        ))
        repository.postTransactions(expense.copy(
            account = AccountType.CASH,
            date = Date(time),
            value = 1.0f
        ))
        repository.postTransactions(expense.copy(
            account = AccountType.CREDIT,
            date = Date(time),
            value = 1.0f
        ))
    }
    @Test
    fun testOnCreate()   = runBlocking{
       launchActivity<DashBoardActivity>()
        Unit // break point here for launch the activity in isolation
    }
}