package com.jparkbro.tododay

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jparkbro.tododay.ui.TododayApp
import com.jparkbro.tododay.ui.splash.SplashViewModel
import com.jparkbro.tododay.ui.theme.TODoDAYTheme
import com.jparkbro.tododay.utils.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
 * Room_Inventory
 * DBInspector
 * WorkManager_BlurOMatic
 * Network_Retrofit_MarsPhoto
 * Network_Amphibians
 * Coroutine_RaceTracker
 * ComposeInstagramClone
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashVM : SplashViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition() {
                splashVM.uiState.value.appVersion
            }
        }

        // splash screen close animation
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 200L

            slideUp.doOnEnd { splashScreenView.remove() }

            slideUp.start()
        }

        setContent {
            TODoDAYTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TododayApp()
                }
            }
        }
    }
}
