package com.campino.expenses.datasource

import com.campino.expenses.domine.TransactionEntity
import com.campino.expenses.model.TransactionDao
import com.campino.expenses.model.toEntities
import com.campino.expenses.model.toTransactionModel
import com.campino.expenses.repository.TransactionDataSource

class TransactionDataSourceImpl(private val dao: TransactionDao) : TransactionDataSource {
    override fun get(account: String): List<TransactionEntity> {
        return dao.find(account)?.toEntities() ?: listOf()
    }
    override fun delete(uid: Long) {
        dao.delete(uid)
    }
    override fun insert(post: TransactionEntity) {
        val data = post.toTransactionModel()
        dao.insert(data)
    }
}