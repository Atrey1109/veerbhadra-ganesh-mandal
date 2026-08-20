package com.veerbhadra.ganeshmandal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: ReceiptEntity): Long

    @Update
    suspend fun updateReceipt(receipt: ReceiptEntity)

    @Delete
    suspend fun deleteReceipt(receipt: ReceiptEntity)

    @Query("SELECT * FROM receipts ORDER BY id DESC")
    fun getAllReceipts(): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE id = :id LIMIT 1")
    suspend fun getReceiptById(id: Long): ReceiptEntity?

    @Query("SELECT COUNT(*) FROM receipts")
    fun getTotalReceiptsCount(): Flow<Int>

    @Query("SELECT IFNULL(SUM(amount), 0.0) FROM receipts")
    fun getTotalCollection(): Flow<Double>

    @Query("SELECT IFNULL(SUM(amount), 0.0) FROM receipts WHERE paymentMode = :mode")
    fun getTotalByMode(mode: String): Flow<Double>

    @Query("SELECT COUNT(DISTINCT donorName) FROM receipts")
    fun getTotalUniqueDonors(): Flow<Int>

    @Query("SELECT * FROM receipts ORDER BY id ASC")
    suspend fun getAllReceiptsRaw(): List<ReceiptEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(receipts: List<ReceiptEntity>)
}
