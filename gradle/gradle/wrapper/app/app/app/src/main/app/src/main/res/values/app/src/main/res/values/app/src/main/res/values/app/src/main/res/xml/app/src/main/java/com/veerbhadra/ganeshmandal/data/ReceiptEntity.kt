package com.veerbhadra.ganeshmandal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val receiptNo: String,
    val date: String,
    val donorName: String,
    val mobileNumber: String,
    val address: String,
    val amount: Double,
    val paymentMode: String,
    val transactionId: String = "",
    val donationType: String,
    val remark: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
