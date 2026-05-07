package com.example.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InventoryScreenPreview() {
    InventoryScreen(onNavigate = {})
}

@Composable
fun InventoryScreen(
    onNavigate: (Screen) -> Unit
) {
    val gradientColors = listOf(Color(0xFF6DA5FF), Color(0xFFC8D7EB))
    
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
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onNavigate(Screen.Main) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0x33FFFFFF), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Inventory", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
                
                // Grocery list button
                OutlinedButton(
                    onClick = { onNavigate(Screen.GroceryList) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66FFFFFF)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.background(Color(0x33FFFFFF), RoundedCornerShape(20.dp))
                ) {
                    Text("Grocery list", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", modifier = Modifier.size(16.dp))
                }
            }
            
            // Inventory Cards
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                InventoryCard(
                    title = "All items",
                    itemCount = "43 items",
                    icon = Icons.Default.Restaurant,
                    expiringSoonCount = "12 items exp. soon",
                    expiredCount = "3 items exp.",
                    onClick = { onNavigate(Screen.Suggested) }
                )
                InventoryCard(
                    title = "Pantry",
                    itemCount = "15 items",
                    icon = Icons.Default.Store,
                    expiringSoonCount = "5 items exp. soon",
                    expiredCount = "2 items exp.",
                    onClick = { onNavigate(Screen.Suggested) }
                )
                InventoryCard(
                    title = "Fridge",
                    itemCount = "21 items",
                    icon = Icons.Default.Kitchen,
                    expiringSoonCount = "8 items exp. soon",
                    expiredCount = "2 items exp.",
                    onClick = { onNavigate(Screen.Fridge) }
                )
                InventoryCard(
                    title = "Freezer",
                    itemCount = "39 items",
                    icon = Icons.Default.AcUnit,
                    expiringSoonCount = "18 items exp. soon",
                    expiredCount = "9 items exp.",
                    onClick = { onNavigate(Screen.Suggested) }
                )
            }
        }
        
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.Inventory, onNavigate = onNavigate)
        }
    }
}

@Composable
fun InventoryCard(
    title: String,
    itemCount: String,
    icon: ImageVector,
    expiringSoonCount: String,
    expiredCount: String,
    onClick: () -> Unit = {}
) {
    val cardBgColor = Color(0x33FFFFFF)
    val cardBorderColor = Color(0x66FFFFFF)

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, cardBorderColor, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(itemCount, color = Color.LightGray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BadgeText(text = expiringSoonCount, bgColor = Color.Transparent, borderColor = Color.LightGray, textColor = Color.LightGray)
                    BadgeText(text = expiredCount, bgColor = Color(0xFFFF6B6B), borderColor = Color.Transparent, textColor = Color.White)
                }
            }
        }
    }
}

@Composable
fun BadgeText(text: String, bgColor: Color, borderColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = textColor, fontSize = 10.sp)
    }
}
