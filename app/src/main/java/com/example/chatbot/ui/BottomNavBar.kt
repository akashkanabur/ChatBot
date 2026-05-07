package com.example.chatbot.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ripple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(currentScreen = Screen.Main, onNavigate = {})
}

@Composable
fun BottomNavBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFFE2EDFA), RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavTab(
                isSelected = currentScreen == Screen.Main || currentScreen == Screen.Suggested,
                icon = Icons.Default.ChatBubble,
                label = "Chat",
                onClick = { onNavigate(Screen.Main) }
            )
            NavTab(
                isSelected = currentScreen == Screen.Inventory || currentScreen == Screen.GroceryList,
                icon = Icons.Default.Assignment,
                label = "Inventory",
                onClick = { onNavigate(Screen.Inventory) }
            )
            NavTab(
                isSelected = currentScreen == Screen.Fridge || currentScreen == Screen.Appliances || currentScreen is Screen.ApplianceDetail,
                icon = Icons.Default.Kitchen,
                label = "Appliances",
                onClick = { onNavigate(Screen.Appliances) }
            )
            NavTab(
                isSelected = currentScreen == Screen.Profile,
                icon = Icons.Default.Person,
                label = "Profile",
                onClick = { onNavigate(Screen.Profile) }
            )
        }
    }
}

@Composable
fun NavTab(
    isSelected: Boolean,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFD6E4F0) else Color.Transparent,
        animationSpec = tween(durationMillis = 300),
        label = "tabBg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF6B8EFF) else Color(0xFF8B9FB3),
        animationSpec = tween(durationMillis = 300),
        label = "tabContent"
    )

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(24.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFF6B8EFF)),
                onClick = onClick
            )
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
                AnimatedVisibility(
                    visible = isSelected,
                    enter = expandHorizontally(tween(300)) + fadeIn(tween(200)),
                    exit = shrinkHorizontally(tween(300)) + fadeOut(tween(150))
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = label,
                            color = contentColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
    }
}
