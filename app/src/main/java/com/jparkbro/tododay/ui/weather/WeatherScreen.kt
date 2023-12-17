package com.jparkbro.tododay.ui.weather

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_DENIED
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jparkbro.tododay.R
import com.jparkbro.tododay.model.LocationDetails

private const val TAG = "WEATHER_SCREEN"

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherUiState: WeatherUiState
) {
    val context = LocalContext.current
    var isGranted by remember { mutableStateOf(false) }

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PERMISSION_DENIED ||
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
        DeniedPermission(modifier) { isGranted = it }
    } else {
        GrantedPermission(
            weatherUiState = weatherUiState,
            context = context
        )
    }

    LaunchedEffect(isGranted) { }
}

@Composable
fun DeniedPermission(
    modifier: Modifier = Modifier,
    isGranted: (Boolean) -> Unit,
) {
    val locationPermissionState = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions.containsValue(true)
            isGranted(granted)
        }
    )

    Column {
        Button(
            onClick = {
                locationPermissionState.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        ) {
        }
    }
}

@Composable
fun GrantedPermission(
    modifier: Modifier = Modifier,
    weatherUiState: WeatherUiState,
    context: Context,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Magenta)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.Cyan)
        ) {
            Text(text = weatherUiState.weather.temp + stringResource(id = R.string.temp))
            Text(text = stringResource(id = weatherUiState.weather.weather))
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(vertical = 16.dp)
                .background(color = Color.Yellow)
        ) {
            Text(text = "dflbidfibeneifb\nasbeilfblseif\nsbielfabl")
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "image",
                modifier = Modifier
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = weatherUiState.weather.location)
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(id = weatherUiState.weather.dayOfWeek))
            Text(text = "${stringResource(id = weatherUiState.weather.month)} ${weatherUiState.weather.day}")
        }
    }
}