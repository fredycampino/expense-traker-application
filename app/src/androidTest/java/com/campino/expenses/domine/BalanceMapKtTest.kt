package com.campino.expenses.domine

import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.HashMap

class BalanceMapKtTest {

    private var income: IncomeEntity = IncomeEntity(
        uid = 0L,
        account = AccountType.CASH,
        category = IncomeCategory.SALARY,
        date = Calendar.getInstance().time,
        value = 1f
    )
    private var expense = ExpensesEntity(
        uid = 0L,
        account = AccountType.CASH,
        category = ExpenseCategory.ENTERTAINMENT,
        date = Calendar.getInstance().time,
        value = 1f
    )


    @Test
    fun testToList() {
        val list = ArrayList<TransactionEntity>()
        list.add(income.copy(value = 1f))
        list.add(income.copy(value = 1f))
        list.add(expense.copy(value = 0.5f))
        list.add(expense.copy(value = 0.2f))
        val result = list.getBalanceValue()
        Assert.assertEquals(1.3f, result)
    }

    @Test
    fun testGetBalanceValue() {
        val bank = ArrayList<TransactionEntity>()
        bank.add(income.copy(value = 1f, account = AccountType.BANK))
        bank.add(expense.copy(value = 1.5f, account = AccountType.BANK))

        val cash = ArrayList<TransactionEntity>()
        cash.add(income.copy(value = 1f, account = AccountType.CASH))
        cash.add(expense.copy(value = 1.5f, account = AccountType.CASH))

        val card = ArrayList<TransactionEntity>()
        card.add(income.copy(value = 1f, account = AccountType.CREDIT))
        card.add(expense.copy(value = 1.5f, account = AccountType.CREDIT))

        val map = HashMap<AccountType, List<TransactionEntity>>()
        map[AccountType.BANK] = bank
        map[AccountType.CASH] = cash
        map[AccountType.CREDIT] = card

        val listItems = map.toList(10)

        Assert.assertTrue(listItems[0] is BalanceEntity)
        Assert.assertTrue(listItems[0].value == -0.5f)
        Assert.assertTrue(listItems[1].account == listItems[0].account)
        Assert.assertTrue(listItems[2].account == listItems[0].account)

        Assert.assertTrue(listItems[3] is BalanceEntity)
        Assert.assertTrue(listItems[3].value == -0.5f)
        Assert.assertTrue(listItems[4].account == listItems[3].account)
        Assert.assertTrue(listItems[5].account == listItems[3].account)

        Assert.assertTrue(listItems[6] is BalanceEntity)
        Assert.assertTrue(listItems[6].value == -0.5f)
        Assert.assertTrue(listItems[7].account == listItems[6].account)
        Assert.assertTrue(listItems[8].account == listItems[6].account)
    }
}