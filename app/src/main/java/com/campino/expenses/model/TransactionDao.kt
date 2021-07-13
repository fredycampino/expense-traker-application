package com.campino.expenses.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.campino.expenses.domine.*
import java.util.*

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactionModel WHERE  account= :account ORDER BY date DESC")
    fun find(account: String): List<TransactionModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: TransactionModel): Unit

    @Query("DELETE FROM transactionModel WHERE uid = :uid")
    fun delete(uid: Long)
}

fun TransactionModel.toTransactionEntity(): TransactionEntity {
    return if (expense) ExpensesEntity(
        uid = uid,
        account = AccountType.valueOf(account),
        category = ExpenseCategory.valueOf(category),
        date = Date(date),
        value = value
    ) else IncomeEntity(
        uid = uid,
        account = AccountType.valueOf(account),
        category = IncomeCategory.valueOf(category),
        date = Date(date),
        value = value
    )
}


fun TransactionEntity.toTransactionModel(): TransactionModel {
    val expense = this is ExpensesEntity
    return TransactionModel(
        uid = uid,
        account = account.toString(),
        category = category.toString(),
        date = date.time,
        value = value,
        expense = expense
    )
}

fun List<TransactionModel>.toEntities(): List<TransactionEntity> {
    val list = mutableListOf<TransactionEntity>()
    for (model in this) {
        list.add(model.toTransactionEntity())
    }
    return list
}
