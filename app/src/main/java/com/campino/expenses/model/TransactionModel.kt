package com.campino.expenses.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionModel(
    @PrimaryKey (autoGenerate = true)
    val uid:Long=0,
    val account: String,
    val category: String,
    val expense:Boolean,
    val date: Long,
    val value: Float
)