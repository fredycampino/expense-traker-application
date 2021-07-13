package com.campino.expenses.repository

import com.campino.expenses.domine.AccountType
import com.campino.expenses.domine.IncomeCategory
import com.campino.expenses.domine.IncomeEntity
import com.campino.expenses.inject.InstanceProvides
import com.campino.expenses.tools.testInstrument
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.*

class TransactionRepositoryImplTest {
    private var post: IncomeEntity = IncomeEntity(
        uid = 0L,
        account = AccountType.BANK,
        category = IncomeCategory.SALARY,
        date = Calendar.getInstance().time,
        value = 3000f
    )
    private val instruments: InstanceProvides = testInstrument()


    @Test
    fun testGetTransactions() = runBlocking {
        instruments.deleteAppDatabase()
        val repository = instruments.repository
        var time = Calendar.getInstance().time.time
        for (i in 1..20) {
            repository.postTransactions(post.copy(date = Date(time)))
            time += 86400000L
        }
        val map = repository.getTransactions()
        val bank = map[AccountType.BANK]

        Assert.assertEquals(20, bank?.size)
        Assert.assertTrue(bank?.get(0)?.date!! > bank[1].date)
    }
}