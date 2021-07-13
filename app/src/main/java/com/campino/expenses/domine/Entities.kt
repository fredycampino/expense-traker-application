package com.campino.expenses.domine

import java.util.*

open class TransactionEntity(
    open val uid: Long = 0,
    open val account: AccountType,
    open val category: Any,
    open val date: Date,
    open val value: Float
)

open class BalanceEntity(
    override val account: AccountType,
    override val value: Float
) : TransactionEntity(0, account, "BALANCE", Date(), value)


data class ExpensesEntity(
    override val uid: Long = 0,
    override val account: AccountType,
    override val category: ExpenseCategory,
    override val date: Date,
    override val value: Float
) : TransactionEntity(uid, account, category, date, value)

data class IncomeEntity(
    override val uid: Long = 0,
    override val account: AccountType,
    override val category: IncomeCategory,
    override val date: Date,
    override val value: Float
) : TransactionEntity(uid, account, category, date, value)

interface HasLocale {
    fun getResources()
}

enum class AccountType {
    CASH,
    CREDIT,
    BANK,
}

enum class ExpenseCategory {
    TAX,
    GROCERY,
    ENTERTAINMENT,
    GYM,
    HEALTH,
}

enum class IncomeCategory {
    SALARY,
    DIVIDENDS
}