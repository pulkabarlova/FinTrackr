package com.polina.ui.components

import androidx.compose.ui.graphics.Color

fun generateRandomColor(): Color {
    val colorPalette = listOf(
        Color(0xFFE57373),
        Color(0xFFF06292),
        Color(0xFFBA68C8),
        Color(0xFF9575CD),
        Color(0xFF64B5F6),
        Color(0xFF4DD0E1),
        Color(0xFF4DB6AC),
        Color(0xFF81C784),
        Color(0xFFFFD54F),
        Color(0xFFFF8A65),
        Color(0xFFA1887F),
        Color(0xFF90A4AE)
    )
    return colorPalette.random()
}