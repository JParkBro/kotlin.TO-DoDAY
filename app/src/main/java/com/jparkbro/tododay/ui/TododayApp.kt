package com.jparkbro.tododay.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jparkbro.tododay.ui.navigation.NavigationDestination
import com.jparkbro.tododay.ui.navigation.TododayNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TododayApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val items = listOf(
        NavigationDestination.Weather,
        NavigationDestination.Todo,
        NavigationDestination.DDay,
        NavigationDestination.Setting,
    )
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = modifier
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription = item.route) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                    )
                }
            }
        }
    ) { innerPadding ->
        TododayNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    TododayApp()
}