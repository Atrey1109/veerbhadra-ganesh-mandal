package com.veerbhadra.ganeshmandal.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.veerbhadra.ganeshmandal.ui.viewmodel.MandalViewModel
import com.veerbhadra.ganeshmandal.util.BackupHelper

@Composable
fun SettingsBackupScreen(viewModel: MandalViewModel) {
val context = LocalContext.current
val receipts by viewModel.allReceipts.collectAsState()

Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {  
    Text("डेटा बॅकअप आणि व्यवस्थापन", style = MaterialTheme.typography.titleLarge)  
    Spacer(modifier = Modifier.height(20.dp))  
    Button(  
        onClick = {  
            if (receipts.isEmpty()) {  
                Toast.makeText(context, "कोणताही डेटा उपलब्ध नाही!", Toast.LENGTH_SHORT).show()  
                return@Button  
            }  
            val file = BackupHelper.exportToCsv(context, receipts)  
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)  
            val intent = Intent(Intent.ACTION_SEND).apply {  
                type = "text/csv"  
                putExtra(Intent.EXTRA_STREAM, uri)  
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)  
            }  
            context.startActivity(Intent.createChooser(intent, "CSV बॅकअप फाइल शेअर करा"))  
        },  
        modifier = Modifier.fillMaxWidth()  
    ) {  
        Text("Export CSV Backup")  
    }  
}

}
