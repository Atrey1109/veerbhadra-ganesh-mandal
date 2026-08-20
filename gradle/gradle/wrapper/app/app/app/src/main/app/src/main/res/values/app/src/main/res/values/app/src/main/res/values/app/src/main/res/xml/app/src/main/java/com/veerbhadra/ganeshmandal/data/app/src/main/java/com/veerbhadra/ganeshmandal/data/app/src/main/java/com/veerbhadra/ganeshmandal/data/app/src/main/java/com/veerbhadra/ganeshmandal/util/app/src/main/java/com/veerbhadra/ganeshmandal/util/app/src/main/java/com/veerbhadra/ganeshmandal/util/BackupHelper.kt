package com.veerbhadra.ganeshmandal.util

import android.content.Context
import com.veerbhadra.ganeshmandal.data.ReceiptEntity
import java.io.File
import java.io.FileWriter

object BackupHelper {
    fun exportToCsv(context: Context, receipts: List<ReceiptEntity>): File {
        val file = File(context.cacheDir, "veerbhadra_records_backup.csv")
        val writer = FileWriter(file)
        writer.append("ID,ReceiptNo,Date,DonorName,Mobile,Address,Amount,PaymentMode,TransactionID,Type,Remark\n")
        receipts.forEach {
            writer.append("${it.id},${it.receiptNo},${it.date},\"${it.donorName}\",${it.mobileNumber},\"${it.address}\",${it.amount},${it.paymentMode},\"${it.transactionId}\",${it.donationType},\"${it.remark}\"\n")
        }
        writer.flush()
        writer.close()
        return file
    }
}
