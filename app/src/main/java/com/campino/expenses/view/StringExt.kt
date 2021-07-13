package com.campino.expenses.view

import android.content.Context
import android.text.format.DateUtils
import com.campino.expenses.R
import com.campino.expenses.domine.AccountType
import com.campino.expenses.domine.ExpenseCategory
import com.campino.expenses.domine.IncomeCategory
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

inline fun <reified T : Enum<T>> enumStrings(toString: ((item: Enum<T>) -> String)): List<String> {
    val list = enumValues<T>()
    val values = mutableListOf<String>()
    for (item in list) {
        val str = toString(item)
        values.add(str)
    }
    return values
}

fun AccountType.getResource(): Int {
    return when (this) {
        AccountType.BANK -> R.string.label_bank
        AccountType.CASH -> R.string.label_cash
        AccountType.CREDIT -> R.string.label_credit
    }
}

fun String.toAccountType(context: Context): AccountType? {
    return when (this) {
        context.getString(R.string.label_bank) -> AccountType.BANK
        context.getString(R.string.label_cash) -> AccountType.CASH
        context.getString(R.string.label_credit) -> AccountType.CREDIT
        else -> null
    }
}


fun ExpenseCategory.getResource(): Int {
    return when (this) {
        ExpenseCategory.TAX -> R.string.label_tax
        ExpenseCategory.GROCERY -> R.string.label_grocery
        ExpenseCategory.ENTERTAINMENT -> R.string.label_entertainment
        ExpenseCategory.GYM -> R.string.label_gym
        ExpenseCategory.HEALTH -> R.string.label_health
    }
}

fun String.toExpenseCategory(context: Context): ExpenseCategory? {
    return when (this) {
        context.getString(R.string.label_tax) -> ExpenseCategory.TAX
        context.getString(R.string.label_grocery) -> ExpenseCategory.GROCERY
        context.getString(R.string.label_entertainment) -> ExpenseCategory.ENTERTAINMENT
        context.getString(R.string.label_gym) -> ExpenseCategory.GYM
        context.getString(R.string.label_health) -> ExpenseCategory.HEALTH
        else -> null
    }
}

fun IncomeCategory.getResource(): Int {
    return when (this) {
        IncomeCategory.SALARY -> R.string.label_salary
        IncomeCategory.DIVIDENDS -> R.string.label_dividends
    }
}

fun String.toIncomeCategory(context: Context): IncomeCategory? {
    return when (this) {
        context.getString(R.string.label_salary) -> IncomeCategory.SALARY
        context.getString(R.string.label_dividends) -> IncomeCategory.DIVIDENDS
        else -> null
    }
}


fun Float.toLocaleCurrency(): String {
    val d: DecimalFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
    return d.format(this)
}


fun Date.toRelative(): String {
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(
        time, now,
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString()
}

