package com.veerbhadra.ganeshmandal.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veerbhadra.ganeshmandal.data.ReceiptEntity
import com.veerbhadra.ganeshmandal.ui.viewmodel.MandalViewModel

@Composable
fun NewReceiptScreen(viewModel: MandalViewModel, onSaved: () -> Unit) {
val context = LocalContext.current
val totalReceipts by viewModel.totalReceiptsCount.collectAsState()

var receiptNo by remember(totalReceipts) { mutableStateOf(viewModel.getNextReceiptNumber(totalReceipts)) }  
var date by remember { mutableStateOf(viewModel.getTodayDate()) }  
var donorName by remember { mutableStateOf("") }  
var mobile by remember { mutableStateOf("") }  
var address by remember { mutableStateOf("") }  
var amount by remember { mutableStateOf("") }  
var paymentMode by remember { mutableStateOf("Cash") }  
var transactionId by remember { mutableStateOf("") }  
var donationType by remember { mutableStateOf("देणगी") }  
var remark by remember { mutableStateOf("") }  

val paymentModes = listOf("Cash", "UPI", "Bank", "Other")  
val donationTypes = listOf("देणगी", "वर्गणी", "इतर")  

Column(  
    modifier = Modifier  
        .fillMaxSize()  
        .padding(16.dp)  
        .verticalScroll(rememberScrollState())  
) {  
    OutlinedTextField(  
        value = receiptNo,  
        onValueChange = { receiptNo = it },  
        label = { Text("पावती क्र.") },  
        modifier = Modifier.fillMaxWidth(),  
        readOnly = true  
    )  
    Spacer(modifier = Modifier.height(8.dp))  
    OutlinedTextField(  
        value = date,  
        onValueChange = { date = it },  
        label = { Text("दिनांक (DD/MM/YYYY)") },  
        modifier = Modifier.fillMaxWidth()  
    )  
    Spacer(modifier = Modifier.height(8.dp))  
    OutlinedTextField(  
        value = donorName,  
        onValueChange = { donorName = it },  
        label = { Text("देणगीदाराचे नाव *") },  
        modifier = Modifier.fillMaxWidth()  
    )  
    Spacer(modifier = Modifier.height(8.dp))  
    OutlinedTextField(  
        value = mobile,  
        onValueChange = { mobile = it },  
        label = { Text("मोबाईल क्रमांक") },  
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),  
        modifier = Modifier.fillMaxWidth()  
    )  
    Spacer(modifier = Modifier.height(8.dp))  
    OutlinedTextField(  
        value = address,  
        onValueChange = { address = it },  
        label = { Text("पत्ता / परिसर") },  
        modifier = Modifier.fillMaxWidth()  
    )  
    Spacer(modifier = Modifier.height(8.dp))  
    OutlinedTextField(  
        value = amount,  
        onValueChange = { amount = it },  
        label = { Text("रक्कम (₹) *") },  
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  
        modifier = Modifier.fillMaxWidth()  
    )  
    Spacer(modifier = Modifier.height(12.dp))  

    Text("प्रकार (Type):")  
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {  
        donationTypes.forEach { type ->  
            FilterChip(  
                selected = donationType == type,  
                onClick = { donationType = type },  
                label = { Text(type) }  
            )  
        }  
    }  

    Spacer(modifier = Modifier.height(8.dp))  
    Text("भरणा पद्धत (Payment Mode):")  
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {  
        paymentModes.forEach { mode ->  
            FilterChip(  
                selected = paymentMode == mode,  
                onClick = { paymentMode = mode },  
                label = { Text(mode) }  
            )  
        }  
    }  

    if (paymentMode != "Cash") {  
        Spacer(modifier = Modifier.height(8.dp))  
        OutlinedTextField(  
            value = transactionId,  
            onValueChange = { transactionId = it },  
            label = { Text("संदर्भ क्रमांक / Transaction ID") },  
            modifier = Modifier.fillMaxWidth()  
        )  
    }  

    Spacer(modifier = Modifier.height(8.dp))  
    OutlinedTextField(  
        value = remark,  
        onValueChange = { remark = it },  
        label = { Text("टीप (Remark)") },  
        modifier = Modifier.fillMaxWidth()  
    )  

    Spacer(modifier = Modifier.height(20.dp))  
    Button(  
        onClick = {  
            val amt = amount.toDoubleOrNull()  
            if (donorName.isBlank() || amt == null || amt <= 0.0) {  
                Toast.makeText(context, "कृपया नाव आणि योग्य रक्कम टाका", Toast.LENGTH_SHORT).show()  
                return@Button  
            }  

            val receipt = ReceiptEntity(  
                receiptNo = receiptNo,  
                date = date,  
                donorName = donorName.trim(),  
                mobileNumber = mobile.trim(),  
                address = address.trim(),  
                amount = amt,  
                paymentMode = paymentMode,  
                transactionId = transactionId.trim(),  
                donationType = donationType,  
                remark = remark.trim()  
            )  

            viewModel.saveReceipt(receipt) {  
                Toast.makeText(context, "पावती जतन झाली!", Toast.LENGTH_SHORT).show()  
                onSaved()  
            }  
        },  
        modifier = Modifier  
            .fillMaxWidth()  
            .height(52.dp),  
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)  
    ) {  
        Text("पावती सेव्ह करा", fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary)  
    }  
}

}
