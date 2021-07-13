package com.campino.expenses.domine


fun HashMap<AccountType, List<TransactionEntity>>.toList(limit: Int): List<TransactionEntity> {
    val accounts = enumValues<AccountType>()
    val transactions = mutableListOf<TransactionEntity>()
    for (key in accounts) {
        val list = get(key) ?: listOf()
        val balance = list.getBalanceValue()
        transactions.add(BalanceEntity(key, balance))
        transactions.addAll(list.take(limit))
    }
    return transactions
}

fun List<TransactionEntity>.getBalanceValue(): Float {
    var balance = 0f
    for (tx in this) {
        balance += if (tx is ExpensesEntity) -1 * tx.value else tx.value
    }
    return balance
}