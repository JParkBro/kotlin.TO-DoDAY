package com.jparkbro.tododay.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SettingScreen() {
    Box(
        modifier = Modifier
            .background(color = Color.Yellow)
    ) {
        Text(text = "Setting Screen")
    }
}