package com.jparkbro.tododay.ui.dday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DDayEditScreen() {
    Box(
        modifier = Modifier
            .background(color = Color.Magenta)
    ) {
        Text(text = "D-Day Edit Screen")
    }
}