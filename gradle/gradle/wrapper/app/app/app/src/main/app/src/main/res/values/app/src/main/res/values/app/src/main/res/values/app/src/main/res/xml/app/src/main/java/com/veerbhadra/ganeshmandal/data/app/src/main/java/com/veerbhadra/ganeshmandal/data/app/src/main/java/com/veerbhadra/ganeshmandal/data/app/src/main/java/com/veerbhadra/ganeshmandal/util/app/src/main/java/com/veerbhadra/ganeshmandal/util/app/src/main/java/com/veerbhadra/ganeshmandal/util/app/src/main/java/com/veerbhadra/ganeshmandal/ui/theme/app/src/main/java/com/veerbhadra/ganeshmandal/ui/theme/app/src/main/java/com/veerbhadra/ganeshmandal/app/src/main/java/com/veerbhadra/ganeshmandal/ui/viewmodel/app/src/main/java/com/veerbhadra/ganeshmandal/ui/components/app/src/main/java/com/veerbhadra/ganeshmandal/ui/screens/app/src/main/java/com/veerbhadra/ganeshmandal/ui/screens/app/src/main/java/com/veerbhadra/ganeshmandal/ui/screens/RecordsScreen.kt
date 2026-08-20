package com.veerbhadra.ganeshmandal.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.veerbhadra.ganeshmandal.data.ReceiptEntity
import com.veerbhadra.ganeshmandal.ui.viewmodel.MandalViewModel
import com.veerbhadra.ganeshmandal.util.PdfGenerator

@Composable
fun RecordsScreen(viewModel: MandalViewModel) {
val receipts by viewModel.allReceipts.collectAsState()
var searchQuery by remember { mutableStateOf("") }
var selectedFilter by remember { mutableStateOf("All") }
var receiptToDelete by remember { mutableStateOf<ReceiptEntity?>(null) }
val context = LocalContext.current

val filteredList = receipts.filter {  
    val matchesSearch = it.donorName.contains(searchQuery, ignoreCase = true) ||  
            it.receiptNo.contains(searchQuery, ignoreCase = true) ||  
            it.mobileNumber.contains(searchQuery)  
    val matchesFilter = when (selectedFilter) {  
        "All" -> true  
        else -> it.paymentMode == selectedFilter  
    }  
    matchesSearch && matchesFilter  
}  

if (receiptToDelete != null) {  
    AlertDialog(  
        onDismissRequest = { receiptToDelete = null },  
        title = { Text("पावती हटवा") },  
        text = { Text("ही पावती कायमची delete करायची आहे का?") },  
        confirmButton = {  
            TextButton(onClick = {  
                receiptToDelete?.let { viewModel.deleteReceipt(it) }  
                receiptToDelete = null  
            }) {  
                Text("हटवा (Delete)", color = MaterialTheme.colorScheme.error)  
            }  
        },  
        dismissButton = {  
            TextButton(onClick = { receiptToDelete = null }) { Text("रद्द करा") }  
        }  
    )  
}  

Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {  
    OutlinedTextField(  
        value = searchQuery,  
        onValueChange = { searchQuery = it },  
        placeholder = { Text("नाव, पावती क्र., मोबाईल शोधा...") },  
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },  
        modifier = Modifier.fillMaxWidth()  
    )  

    Spacer(modifier = Modifier.height(8.dp))  

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {  
        listOf("All", "Cash", "UPI", "Bank").forEach { mode ->  
            FilterChip(  
                selected = selectedFilter == mode,  
                onClick = { selectedFilter = mode },  
                label = { Text(mode) }  
            )  
        }  
    }  

    Spacer(modifier = Modifier.height(12.dp))  

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {  
        items(filteredList, key = { it.id }) { receipt ->  
            Card(  
                modifier = Modifier.fillMaxWidth(),  
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)  
            ) {  
                Column(modifier = Modifier.padding(14.dp)) {  
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {  
                        Text(receipt.receiptNo, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)  
                        Text(receipt.date, fontSize = 12.sp)  
                    }  
                    Text(receipt.donorName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)  
                    Text("₹ ${receipt.amount}  •  ${receipt.paymentMode}  •  ${receipt.donationType}", fontSize = 13.sp)  

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {  
                        IconButton(onClick = {  
                            val pdfFile = PdfGenerator.generateReceiptPdf(context, receipt)  
                            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", pdfFile)  
                            val intent = Intent(Intent.ACTION_SEND).apply {  
                                type = "application/pdf"  
                                putExtra(Intent.EXTRA_STREAM, uri)  
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)  
                            }  
                            context.startActivity(Intent.createChooser(intent, "पावती शेअर करा"))  
                        }) {  
                            Icon(Icons.Default.Share, contentDescription = "Share PDF", tint = MaterialTheme.colorScheme.primary)  
                        }  
                        IconButton(onClick = { receiptToDelete = receipt }) {  
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)  
                        }  
                    }  
                }  
            }  
        }  
    }  
}

}
