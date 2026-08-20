package com.veerbhadra.ganeshmandal.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "मुख्यपृष्ठ", Icons.Default.Dashboard)
    object NewReceipt : Screen("new_receipt", "नवी पावती", Icons.Default.AddCircle)
    object Records : Screen("records", "पावत्या", Icons.Default.ListAlt)
    object Reports : Screen("reports", "अहवाल", Icons.Default.Assessment)
    object Settings : Screen("settings", "सेटिंग्ज", Icons.Default.Settings)
}

@Composable
fun MandalBottomNav(currentRoute: String, onNavigate: (String) -> Unit) {
    val items = listOf(
        Screen.Dashboard,
        Screen.NewReceipt,
        Screen.Records,
        Screen.Reports,
        Screen.Settings
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                ),
                onClick = { onNavigate(item.route) }
            )
        }
    }
}
