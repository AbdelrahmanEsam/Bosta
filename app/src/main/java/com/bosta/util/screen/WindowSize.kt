package com.bosta.util.screen

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.window.layout.WindowMetricsCalculator

@Composable
fun rememberWindowSizeDp(activity: Activity) : DpSize {
    val configuration = LocalConfiguration.current

    val windowMetrics = remember(configuration)
    {
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    }

    val windowSizeDp = with(LocalDensity.current){
        windowMetrics.bounds.toComposeRect().size.toDpSize()
    }

    return windowSizeDp
}