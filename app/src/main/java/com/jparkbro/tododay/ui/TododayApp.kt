package com.jparkbro.tododay.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jparkbro.tododay.model.LocationDetails
import com.jparkbro.tododay.ui.navigation.BottomNavigationBar
import com.jparkbro.tododay.ui.navigation.TododayNavHost
import com.jparkbro.tododay.ui.navigation.bottomItems
import com.jparkbro.tododay.utils.LocationViewModel

private const val TAG = "TO_DO_DAY_APP"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TododayApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    location: LocationDetails?
) {

    Log.d(TAG, location.toString())

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var isBottomBarVisible = false
    currentDestination?.route?.let { route ->
        isBottomBarVisible = bottomItems.any { it.route == route }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (isBottomBarVisible) BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        TododayNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            location = location,
        )
    }
}