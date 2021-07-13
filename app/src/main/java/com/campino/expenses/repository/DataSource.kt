package com.campino.expenses.repository

import com.campino.expenses.domine.TransactionEntity


interface TransactionDataSource{
    fun insert(post: TransactionEntity)
    fun get(account: String): List<TransactionEntity>
    fun delete(uid:Long)
}