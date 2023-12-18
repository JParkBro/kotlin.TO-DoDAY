package com.jparkbro.tododay.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun InfoScreenSecond(
    navigateToNext: () -> Unit,
    navigateToBefore: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow)
    ) {
        Column {
            Text(text = "Info Second Screen")
            Button(
                onClick = { navigateToNext() }
            ) {
                Text(text = "Next")
            }
            Button(
                onClick = { navigateToBefore() }
            ) {
                Text(text = "Before")
            }
        }
    }
}