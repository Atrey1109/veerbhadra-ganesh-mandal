package com.veerbhadra.ganeshmandal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veerbhadra.ganeshmandal.ui.viewmodel.MandalViewModel

@Composable
fun DashboardScreen(viewModel: MandalViewModel) {
val total by viewModel.totalCollection.collectAsState()
val receiptsCount by viewModel.totalReceiptsCount.collectAsState()
val donorsCount by viewModel.totalDonors.collectAsState()
val cash by viewModel.cashTotal.collectAsState()
val upi by viewModel.upiTotal.collectAsState()
val bank by viewModel.bankTotal.collectAsState()
val other by viewModel.otherTotal.collectAsState()

Column(  
    modifier = Modifier  
        .fillMaxSize()  
        .padding(16.dp)  
) {  
    Card(  
        modifier = Modifier.fillMaxWidth(),  
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)  
    ) {  
        Column(modifier = Modifier.padding(20.dp)) {  
            Text("॥ श्री गणेशाय नमः ॥", color = MaterialTheme.colorScheme.secondary, fontSize = 14.sp)  
            Text("वीरभद्र गणेश मंडळ", color = MaterialTheme.colorScheme.onPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)  
            Text("मंगळवार पेठ, वसमतनगर (स्था. २०२२)", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f), fontSize = 12.sp)  
            Spacer(modifier = Modifier.height(16.dp))  
            Text("एकूण जमा रक्कम (Total Collection)", color = MaterialTheme.colorScheme.secondary, fontSize = 14.sp)  
            Text("₹ ${"%,.2f".format(total)}", color = MaterialTheme.colorScheme.onPrimary, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)  
        }  
    }  

    Spacer(modifier = Modifier.height(16.dp))  

    LazyVerticalGrid(  
        columns = GridCells.Fixed(2),  
        horizontalArrangement = Arrangement.spacedBy(12.dp),  
        verticalArrangement = Arrangement.spacedBy(12.dp),  
        modifier = Modifier.fillMaxSize()  
    ) {  
        item { MetricCard("एकूण देणगीदार", "$donorsCount जण") }  
        item { MetricCard("एकूण पावत्या", "$receiptsCount") }  
        item { MetricCard("रोख (Cash)", "₹ ${"%,.2f".format(cash)}") }  
        item { MetricCard("UPI", "₹ ${"%,.2f".format(upi)}") }  
        item { MetricCard("बँक (Bank)", "₹ ${"%,.2f".format(bank)}") }  
        item { MetricCard("इतर (Other)", "₹ ${"%,.2f".format(other)}") }  
    }  
}

}

@Composable
fun MetricCard(title: String, value: String) {
Card(
elevation = CardDefaults.cardElevation(2.dp),
colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
) {
Column(modifier = Modifier.padding(16.dp)) {
Text(title, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), fontSize = 13.sp)
Spacer(modifier = Modifier.height(6.dp))
Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
}
}
}
