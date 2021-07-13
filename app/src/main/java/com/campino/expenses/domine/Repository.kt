package com.campino.expenses.domine

interface TransactionsRepository {
    suspend fun postTransactions(post: TransactionEntity)
    suspend fun getTransactions(): HashMap<AccountType, List<TransactionEntity>>
    suspend fun delete(entity: TransactionEntity)
}