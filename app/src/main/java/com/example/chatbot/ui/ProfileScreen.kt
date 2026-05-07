package com.example.chatbot.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(isDarkTheme = false, onThemeToggle = {}, onNavigate = {})
}

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    onNavigate: (Screen) -> Unit
) {
    // Colors adapt to theme
    val bgGradient = if (isDarkTheme)
        listOf(Color(0xFF1A1F2E), Color(0xFF2C353F))
    else
        listOf(Color(0xFF6DA5FF), Color(0xFFC8D7EB))

    val cardBg = if (isDarkTheme) Color(0xFF2C353F) else Color(0x33FFFFFF)
    val cardBorder = if (isDarkTheme) Color(0xFF3B4652) else Color(0x66FFFFFF)
    val labelColor = if (isDarkTheme) Color(0xFFB0BEC5) else Color(0xFFE8F0FE)
    val valueColor = Color.White
    val dividerColor = if (isDarkTheme) Color(0xFF3B4652) else Color(0x44FFFFFF)

    val scope = rememberCoroutineScope()

    var userName by remember { mutableStateOf("Heer") }
    var editingName by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf(userName) }
    var household by remember { mutableStateOf("2 members") }
    var location by remember { mutableStateOf("Mumbai, India") }
    var dietary by remember { mutableStateOf("Vegetarian") }
    var notifications by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Entry animations
    val avatarScale = remember { Animatable(0.5f) }
    val headerAlpha = remember { Animatable(0f) }
    val cardAlpha = remember { Animatable(0f) }
    val cardOffset = remember { Animatable(40f) }
    val buttonsAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch { avatarScale.animateTo(1f, tween(400)) }
        launch { headerAlpha.animateTo(1f, tween(350)) }
        delay(150)
        launch {
            cardAlpha.animateTo(1f, tween(400))
            cardOffset.animateTo(0f, tween(400))
        }
        delay(300)
        launch { buttonsAlpha.animateTo(1f, tween(350)) }
    }

    // Animate background gradient transition
    val bgTopColor by animateColorAsState(bgGradient[0], tween(500), label = "bgTop")
    val bgBottomColor by animateColorAsState(bgGradient[1], tween(500), label = "bgBottom")

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = Color(0xFF2C353F),
            title = { Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to log out?", color = Color(0xFFB0BEC5)) },
            confirmButton = {
                TextButton(onClick = { showLogoutDialog = false; onNavigate(Screen.Main) }) {
                    Text("Log Out", color = Color(0xFFFF6B6B))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = Color(0xFF6B8EFF))
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(bgTopColor, bgBottomColor)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 48.dp, start = 24.dp, end = 24.dp, bottom = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .alpha(headerAlpha.value)
            ) {
                IconButton(
                    onClick = { onNavigate(Screen.Main) },
                    modifier = Modifier.size(48.dp).background(Color(0x33FFFFFF), CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Profile", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            // Avatar
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .scale(avatarScale.value)
                    .background(Color(0x33FFFFFF), CircleShape)
                    .border(2.dp, Color(0x66FFFFFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Avatar", tint = Color.White, modifier = Modifier.size(56.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Editable name inline
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(headerAlpha.value)
            ) {
                if (editingName) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF6B8EFF),
                            unfocusedBorderColor = Color(0x66FFFFFF),
                            cursorColor = Color.White
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.width(180.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { userName = nameInput; editingName = false },
                        modifier = Modifier.size(36.dp).background(Color(0xFF6B8EFF), CircleShape)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                } else {
                    Text(userName, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { nameInput = userName; editingName = true },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit name", tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                    }
                }
            }

            Text(
                "heer@example.com",
                color = labelColor,
                fontSize = 14.sp,
                modifier = Modifier.alpha(headerAlpha.value)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Info Card
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, cardBorder, RoundedCornerShape(16.dp))
                    .alpha(cardAlpha.value)
                    .offset(y = cardOffset.value.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {

                    DropdownProfileRow(
                        label = "Household",
                        value = household,
                        options = listOf("1 member", "2 members", "3 members", "4 members", "5+ members"),
                        labelColor = labelColor,
                        valueColor = valueColor,
                        onSelect = { household = it }
                    )
                    HorizontalDivider(color = dividerColor)

                    DropdownProfileRow(
                        label = "Location",
                        value = location,
                        options = listOf("Mumbai, India", "Delhi, India", "Bangalore, India", "Chennai, India", "Other"),
                        labelColor = labelColor,
                        valueColor = valueColor,
                        onSelect = { location = it }
                    )
                    HorizontalDivider(color = dividerColor)

                    DropdownProfileRow(
                        label = "Dietary",
                        value = dietary,
                        options = listOf("Vegetarian", "Vegan", "Non-Vegetarian", "Eggetarian", "Gluten-Free"),
                        labelColor = labelColor,
                        valueColor = valueColor,
                        onSelect = { dietary = it }
                    )
                    HorizontalDivider(color = dividerColor)

                    // Notifications toggle
                    ProfileToggleRow(
                        label = "Notifications",
                        checked = notifications,
                        labelColor = labelColor,
                        onCheckedChange = { notifications = it }
                    )
                    HorizontalDivider(color = dividerColor)

                    // Dark / Light theme toggle
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = "Theme",
                                tint = if (isDarkTheme) Color(0xFF6B8EFF) else Color(0xFFFFC107),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                if (isDarkTheme) "Dark Theme" else "Light Theme",
                                color = valueColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { onThemeToggle(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF6B8EFF),
                                uncheckedThumbColor = Color(0xFFFFC107),
                                uncheckedTrackColor = Color(0x44FFC107)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout Button
            AnimatedVisibility(
                visible = buttonsAlpha.value > 0.5f,
                enter = fadeIn(tween(350)) + slideInVertically(tween(350)) { it / 2 }
            ) {
                OutlinedButton(
                    onClick = { showLogoutDialog = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF6B6B)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF6B6B)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log Out", fontSize = 16.sp)
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavBar(currentScreen = Screen.Profile, onNavigate = onNavigate)
        }
    }
}

@Composable
private fun DropdownProfileRow(
    label: String,
    value: String,
    options: List<String>,
    labelColor: Color,
    valueColor: Color,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = labelColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0x22FFFFFF), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = { expanded = true }, modifier = Modifier.size(20.dp)) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Edit $label", tint = valueColor, modifier = Modifier.size(16.dp))
                }
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF2C353F))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = if (option == value) Color(0xFF6B8EFF) else Color.White,
                            fontWeight = if (option == value) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = { onSelect(option); expanded = false }
                )
            }
        }
    }
}

@Composable
private fun ProfileToggleRow(
    label: String,
    checked: Boolean,
    labelColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = labelColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF6B8EFF),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0x44FFFFFF)
            )
        )
    }
}
