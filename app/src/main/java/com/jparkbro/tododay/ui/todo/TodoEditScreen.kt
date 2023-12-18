package com.jparkbro.tododay.ui.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TodoEditScreen() {
    Box(
        modifier = Modifier
            .background(color = Color.Red)
    ) {
        Text(text = "Todo Edit Screen")
    }
}