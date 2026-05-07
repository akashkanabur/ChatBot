package com.example.chatbot.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PlayArrow
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
fun AppliancesScreenPreview() {
    AppliancesScreen(onNavigate = {})
}

data class Appliance(val id: String, val title: String, val imageRes: Int, val isCritical: Boolean)

@Composable
fun AppliancesScreen(onNavigate: (Screen) -> Unit) {
    val gradientColors = listOf(Color(0xFF6DA5FF), Color(0xFFC8D7EB))

    val allAppliances = listOf(
        Appliance("refrigerator", "Bosch Series 4\nConvertible Refrigerator", R.drawable.refrig, false),
        Appliance("chimney", "Elica Kitchen Chimney", R.drawable.kitchen_chem, true),
        Appliance("airfryer", "Philips Air Fryer", R.drawable.air_fryer, false)
    )

    var filterExpanded by remember { mutableStateOf(false) }
    var sortMode by remember { mutableStateOf("Default") }

    val sortedAppliances = when (sortMode) {
        "Critical First" -> allAppliances.sortedByDescending { it.isCritical }
        "Name A-Z" -> allAppliances.sortedBy { it.title }
        else -> allAppliances
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
                        onClick = { onNavigate(Screen.Main) },
                        modifier = Modifier.size(48.dp).background(Color(0x33FFFFFF), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Appliances", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }

                Box {
                    IconButton(
                        onClick = { filterExpanded = true },
                        modifier = Modifier.size(48.dp).background(Color(0x33FFFFFF), RoundedCornerShape(16.dp))
                    ) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = filterExpanded,
                        onDismissRequest = { filterExpanded = false },
                        modifier = Modifier.background(Color(0xFF2C353F))
                    ) {
                        listOf("Default", "Critical First", "Name A-Z").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = if (sortMode == option) Color(0xFF6B8EFF) else Color.White) },
                                onClick = { sortMode = option; filterExpanded = false }
                            )
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                sortedAppliances.forEach { appliance ->
                    ApplianceCard(
                        title = appliance.title,
                        imageRes = appliance.imageRes,
                        isCritical = appliance.isCritical,
                        onClick = { onNavigate(Screen.ApplianceDetail(appliance.id)) }
                    )
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.Appliances, onNavigate = onNavigate)
        }
    }
}

@Composable
fun ApplianceCard(title: String, imageRes: Int, isCritical: Boolean = false, onClick: () -> Unit) {
    val cardBgColor = Color(0x33FFFFFF)
    val cardBorderColor = if (isCritical) Color(0xFFFF6B6B) else Color(0x66FFFFFF)

    Card(
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(1.dp, cardBorderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
                    .background(Color(0x22FFFFFF), RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(88.dp)
                        .padding(8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 20.sp)
                    if (isCritical) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("⚠ Critical condition", color = Color(0xFFFF6B6B), fontSize = 12.sp)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxHeight()
                    .border(1.dp, cardBorderColor, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Details", tint = Color.White, modifier = Modifier.size(32.dp))
            }
        }
    }
}
