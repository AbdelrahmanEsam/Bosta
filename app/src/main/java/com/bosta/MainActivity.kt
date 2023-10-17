package com.bosta

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import androidx.window.layout.WindowMetricsCalculator
import com.wagbat.utils.connectivity.ConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val devicePosture = WindowInfoTracker
            .getOrCreate(this)
            .windowLayoutInfo(this)
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = WindowLayoutInfo(emptyList())
            )

        val connectivityStatus = connectivityObserver.observe().stateIn(
            scope = lifecycleScope,
            started = SharingStarted.Eagerly,
            initialValue = ConnectivityObserver.Status.Lost
        )



        setContent {
            val windowSizeDp = rememberWindowSizeDp(this)
            BostaModal(
                devicePosture = devicePosture, windowSizeDp = windowSizeDp,
                connectivityStatus = connectivityStatus
            )
        }
    }
}

@Composable
fun rememberWindowSizeDp(activity: Activity): DpSize {
    val configuration = LocalConfiguration.current

    val windowMetrics = remember(configuration)
    {
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    }

    val windowSizeDp = with(LocalDensity.current) {
        windowMetrics.bounds.toComposeRect().size.toDpSize()
    }

    return windowSizeDp
}