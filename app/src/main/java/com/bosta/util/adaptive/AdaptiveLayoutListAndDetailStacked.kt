package com.bosta.util.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bosta.util.screen.ScreenClassifier

@Composable
fun AdaptiveLayoutListAndDetailStacked(
    screenClassifier: ScreenClassifier.HalfOpened.TableTopMode,
    firstHalf : @Composable () -> Unit,
    secondHalf : @Composable () -> Unit
) {


    Column {

        Box(modifier = Modifier.fillMaxHeight(screenClassifier.hingeSeparationRatio)) {
            firstHalf()
        }

        Box(modifier = Modifier.fillMaxHeight()) {
            secondHalf()
        }
    }


}