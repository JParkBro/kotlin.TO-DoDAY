package com.jparkbro.tododay.ui.weather

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_DENIED
import androidx.hilt.navigation.compose.hiltViewModel
import com.jparkbro.tododay.R
import com.jparkbro.tododay.model.Weather
import com.jparkbro.tododay.ui.common.Loader
import com.jparkbro.tododay.utils.LocationViewModel

private const val TAG = "WEATHER_SCREEN"

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
) {

    // TODO Permission Check 수정

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_DENIED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_DENIED) {
            locationViewModel.updatePermissionStatus(true)
        }
    }

    val permissionState by locationViewModel.permissionState.collectAsState()

    if (!permissionState) {
        DeniedPermission(modifier) { permission ->
            locationViewModel.updatePermissionStatus(permission)
        }
    } else {
        val currentLocation by locationViewModel.getLocationData().observeAsState()

        currentLocation?. let { location ->
            LaunchedEffect(location) {
                weatherViewModel.getWeather(location)
            }
        }
        GrantedPermission(weatherUiState = weatherViewModel.uiState)
    }
}

@Composable
fun DeniedPermission(
    modifier: Modifier = Modifier,
    permission: (Boolean) -> Unit,
) {
    val locationPermissionState = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions.all { it.value }
            permission(granted)

            // TODO 거부 했을 때 로직 추가
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
) {
    when (weatherUiState) {
        is WeatherUiState.Loading -> Loader(
            modifier = modifier.fillMaxSize()
        )
        is WeatherUiState.Success -> WeatherComposeScreen(
            modifier = modifier,
            weather = weatherUiState.weather
        )
    }
}

@Composable
fun WeatherComposeScreen(
    modifier: Modifier = Modifier,
    weather: Weather,
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
            Text(text = weather.temp + stringResource(id = R.string.temp))
            Text(text = stringResource(id = weather.weather))
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
                Text(text = weather.location)
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(id = weather.dayOfWeek))
            Text(text = "${stringResource(id = weather.month)} ${weather.day}")
        }
    }
}