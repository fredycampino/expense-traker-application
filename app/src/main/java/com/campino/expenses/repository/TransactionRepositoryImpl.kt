package com.campino.expenses.repository

import com.campino.expenses.domine.AccountType
import com.campino.expenses.domine.TransactionEntity
import com.campino.expenses.domine.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TransactionRepositoryImpl(private val dataSource: TransactionDataSource) :
    TransactionsRepository {

    override suspend fun getTransactions(): HashMap<AccountType, List<TransactionEntity>> =
        withContext(Dispatchers.IO) {
            val map: HashMap<AccountType, List<TransactionEntity>> = hashMapOf()
            val accountList = enumValues<AccountType>()
            for (account in accountList) {
                val transactions = dataSource.get(account.toString())
                map[account] = transactions
            }
            return@withContext map
        }

    override suspend fun delete(entity: TransactionEntity) = withContext(Dispatchers.IO) {
        if (entity.uid == 0L) throw RuntimeException("Delete TransactionEntity need uid")
        dataSource.delete(entity.uid)
    }

    override suspend fun postTransactions(post: TransactionEntity) = withContext(Dispatchers.IO) {
        dataSource.insert(post)
    }

}