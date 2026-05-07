package com.example.chatbot.ui

sealed class Screen {
    object Main : Screen()
    object Suggested : Screen()
    object Inventory : Screen()
    object GroceryList : Screen()
    object Fridge : Screen()
    object Profile : Screen()
    object Appliances : Screen()
    data class ApplianceDetail(val applianceId: String) : Screen()
}
