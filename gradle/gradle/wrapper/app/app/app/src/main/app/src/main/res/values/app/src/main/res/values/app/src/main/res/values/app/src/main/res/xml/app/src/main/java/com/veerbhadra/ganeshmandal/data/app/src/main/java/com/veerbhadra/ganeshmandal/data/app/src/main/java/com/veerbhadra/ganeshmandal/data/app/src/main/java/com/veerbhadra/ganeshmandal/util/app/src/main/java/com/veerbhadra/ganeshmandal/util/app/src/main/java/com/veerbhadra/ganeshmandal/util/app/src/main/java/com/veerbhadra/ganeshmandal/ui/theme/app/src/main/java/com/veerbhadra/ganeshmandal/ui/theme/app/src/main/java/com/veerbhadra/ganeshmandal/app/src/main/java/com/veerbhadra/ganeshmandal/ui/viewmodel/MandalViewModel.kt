package com.veerbhadra.ganeshmandal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.veerbhadra.ganeshmandal.data.AppDatabase
import com.veerbhadra.ganeshmandal.data.ReceiptEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MandalViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).receiptDao()

    val allReceipts = dao.getAllReceipts().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val totalCollection = dao.getTotalCollection().stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
    val totalDonors = dao.getTotalUniqueDonors().stateIn(viewModelScope, SharingStarted.Lazily, 0)
    val totalReceiptsCount = dao.getTotalReceiptsCount().stateIn(viewModelScope, SharingStarted.Lazily, 0)
    val cashTotal = dao.getTotalByMode("Cash").stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
    val upiTotal = dao.getTotalByMode("UPI").stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
    val bankTotal = dao.getTotalByMode("Bank").stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
    val otherTotal = dao.getTotalByMode("Other").stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun getNextReceiptNumber(count: Int): String {
        return "VRG-%04d".format(count + 1)
    }

    fun saveReceipt(receipt: ReceiptEntity, onComplete: () -> Unit) {
        viewModelScope.launch {
            if (receipt.id == 0L) {
                dao.insertReceipt(receipt)
            } else {
                dao.updateReceipt(receipt)
            }
            onComplete()
        }
    }

    fun deleteReceipt(receipt: ReceiptEntity) {
        viewModelScope.launch {
            dao.deleteReceipt(receipt)
        }
    }

    fun getTodayDate(): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }
}
