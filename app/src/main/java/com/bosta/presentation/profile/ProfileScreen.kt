package com.bosta.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bosta.R
import com.bosta.util.collectAsSharedFlowWithLifeCycle
import com.bosta.util.screen.sdp
import com.wagbat.utils.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfileScreen(
    modifier: Modifier,
    navController: NavHostController,
    connectivityStatus: StateFlow<ConnectivityObserver.Status>,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val connectionStatus = connectivityStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val noInternetLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
    val logoAnimationState = animateLottieCompositionAsState(
        composition = noInternetLottie,
        isPlaying = true,
        speed = 0.5f
    )

    viewModel.error.collectAsSharedFlowWithLifeCycle {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    Column(modifier, verticalArrangement = Arrangement.Center) {
        if (connectionStatus.value != ConnectivityObserver.Status.Available) {
            LottieAnimation(
                noInternetLottie,
                modifier = Modifier
                    .size(100.sdp)
                    .align(CenterHorizontally),
                progress = { logoAnimationState.progress },
                contentScale = ContentScale.Crop,
            )
        } else {
            state.value.albums?.let {

                with(state.value) {
                    Header(userName = userName, address = address, email = email, phone = phone)
                    albums?.let {
                        AlbumsList(albums = it, navController = navController)
                    }
                }
            } ?: run {
                SideEffect {
                    viewModel.onEvent(ProfileIntents.GetUserProfileAndAlbumsData)
                }
                CircularProgressIndicator(
                    modifier = Modifier.align(CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

