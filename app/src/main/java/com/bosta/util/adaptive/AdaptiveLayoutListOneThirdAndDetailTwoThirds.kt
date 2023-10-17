package com.bosta.util.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun AdaptiveLayoutListOneThirdAndDetailTwoThirds(
    firstHalf : @Composable () -> Unit,
    secondHalf : @Composable () -> Unit
) {

    Row {

        Box(modifier = Modifier.fillMaxWidth(0.3f)) {
            firstHalf()
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            secondHalf()
        }
    }


}