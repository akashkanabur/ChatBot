package com.example.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApplianceDetailScreenPreview() {
    ApplianceDetailScreen(applianceId = "chimney", onNavigate = {})
}

@Composable
fun ApplianceDetailScreen(applianceId: String, onNavigate: (Screen) -> Unit) {
    val gradientColors = listOf(Color(0xFF6DA5FF), Color(0xFFC8D7EB))

    val title = when (applianceId) {
        "refrigerator" -> "Refrigerator"
        "chimney" -> "Chimney"
        "airfryer" -> "Air Fryer"
        else -> "Appliance"
    }
    val productDetails = when (applianceId) {
        "refrigerator" -> "Product - Bosch Series 4 Convertible Refrigerator"
        "chimney" -> "Product - Elica Filterless Autoclean Kitchen Chimney"
        "airfryer" -> "Product - Philips Air Fryer, 1500W, 4.2 Liter"
        else -> ""
    }
    val model = when (applianceId) {
        "refrigerator" -> "Model - GNGNG"
        "chimney" -> "Model - HBAHG"
        "airfryer" -> "Model - CWAJD"
        else -> ""
    }

    val isCritical = applianceId == "chimney"
    val healthText = if (isCritical) "! 20% Health - critical condition" else "100% Health - running perfectly"
    val healthColor = if (isCritical) Color(0xFFFF6B6B) else Color.White

    var isHealthExpanded by remember { mutableStateOf(false) }
    var showControlsDialog by remember { mutableStateOf(false) }
    var filterExpanded by remember { mutableStateOf(false) }

    if (showControlsDialog) {
        AlertDialog(
            onDismissRequest = { showControlsDialog = false },
            containerColor = Color(0xFF2C353F),
            title = { Text("$title Controls", color = Color.White, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    when (applianceId) {
                        "refrigerator" -> {
                            Text("Temperature: 4°C", color = Color.White)
                            Text("Freezer Temp: -18°C", color = Color.White)
                            Text("Mode: Eco", color = Color.White)
                        }
                        "chimney" -> {
                            Text("Fan Speed: Medium", color = Color.White)
                            Text("Auto Clean: Scheduled", color = Color.White)
                            Text("Light: On", color = Color.White)
                        }
                        "airfryer" -> {
                            Text("Temperature: 180°C", color = Color.White)
                            Text("Timer: 15 min", color = Color.White)
                            Text("Mode: Air Fry", color = Color.White)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showControlsDialog = false }) {
                    Text("Close", color = Color(0xFF6B8EFF))
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp, bottom = 80.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onNavigate(Screen.Appliances) },
                        modifier = Modifier.size(48.dp).background(Color(0x33FFFFFF), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Appliances", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text(title, color = Color.White, fontSize = 16.sp)
                    }
                }

                Box {
                    IconButton(
                        onClick = { filterExpanded = true },
                        modifier = Modifier.size(48.dp).background(Color(0x33FFFFFF), RoundedCornerShape(16.dp))
                    ) {
                        Icon(Icons.Default.FilterList, contentDescription = "Options", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = filterExpanded,
                        onDismissRequest = { filterExpanded = false },
                        modifier = Modifier.background(Color(0xFF2C353F))
                    ) {
                        DropdownMenuItem(
                            text = { Text("View Manual", color = Color.White) },
                            onClick = { filterExpanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Schedule Maintenance", color = Color.White) },
                            onClick = { filterExpanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Remove Appliance", color = Color(0xFFFF6B6B)) },
                            onClick = { filterExpanded = false; onNavigate(Screen.Appliances) }
                        )
                    }
                }
            }

            Row(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                        .padding(end = 16.dp)
                        .background(Color(0x22FFFFFF), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val imageRes = when (applianceId) {
                        "refrigerator" -> R.drawable.refrig
                        "chimney" -> R.drawable.kitchen_chem
                        "airfryer" -> R.drawable.air_fryer
                        else -> null
                    }
                    if (imageRes != null) {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                                .padding(16.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(0.6f).fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    DetailText(productDetails)
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailText(model)
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(enabled = isCritical) { isHealthExpanded = !isHealthExpanded }
                    ) {
                        Text(
                            text = healthText,
                            color = healthColor,
                            fontSize = 14.sp,
                            fontWeight = if (isCritical) FontWeight.Bold else FontWeight.Normal
                        )
                        if (isCritical) {
                            Icon(
                                imageVector = if (isHealthExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expand",
                                tint = Color.White,
                                modifier = Modifier.padding(start = 4.dp).size(16.dp)
                            )
                        }
                    }

                    if (isHealthExpanded && isCritical) {
                        Spacer(modifier = Modifier.height(8.dp))
                        ErrorPill("Grease Build-up - requires immediate cleaning")
                        Spacer(modifier = Modifier.height(4.dp))
                        ErrorPill("Motor Leaks - check for Water infiltration")
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    DetailText("Warranty expires on - 15/10/2029\n(3 years, 4 months)")
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailText("Last Maintained - 10/01/2026\n(5 months ago)")
                }
            }

            Button(
                onClick = { showControlsDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp)
                    .border(1.dp, Color(0x66FFFFFF), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Appliance Controls", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.ApplianceDetail(applianceId), onNavigate = onNavigate)
        }
    }
}

@Composable
fun DetailText(text: String) {
    Text(text = text, color = Color.White, fontSize = 14.sp, lineHeight = 20.sp)
}

@Composable
fun ErrorPill(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0x33FFFFFF), RoundedCornerShape(16.dp))
            .border(1.dp, Color(0x66FFFFFF), RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = Color.White, fontSize = 10.sp)
    }
}
