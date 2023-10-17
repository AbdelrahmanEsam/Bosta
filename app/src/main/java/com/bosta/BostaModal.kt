package com.bosta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowLayoutInfo
import com.bosta.navigation.BostaNavGraph
import com.bosta.util.designsystem.ui.theme.MyApplicationTheme
import com.wagbat.utils.connectivity.ConnectivityObserver
import com.bosta.util.screen.ScreenClassifier
import com.wagbat.utils.screen.ScreenInfo
import com.bosta.util.screen.WindowSizeClass
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BostaModal(
    devicePosture: StateFlow<WindowLayoutInfo>,
    windowSizeDp: DpSize,
    connectivityStatus: StateFlow<ConnectivityObserver.Status>,
) {
    val devicePostureValue by devicePosture.collectAsStateWithLifecycle()
    MyApplicationTheme {
        val screenClassifier = ScreenInfo().screenClassifier(
            windowDpSize = windowSizeDp,
            devicePosture = devicePostureValue
        )
        val isCompactAndHome =
            screenClassifier is ScreenClassifier.FullyOpened && (screenClassifier.width.sizeClass == WindowSizeClass.Compact || screenClassifier.height.sizeClass == WindowSizeClass.Compact)
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Scaffold { paddingValues ->
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        BostaNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(paddingValues),
                            scaffoldState = scaffoldState,
                            connectivityStatus = connectivityStatus,
                        )
                    }
                }
            }

        }
    }

}


@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        drawerState
    } else {
        DrawerState(DrawerValue.Closed)
    }
}