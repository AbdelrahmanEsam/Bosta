package com.bosta.presentation.album

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bosta.R
import com.bosta.presentation.image.ImageDialog
import com.bosta.util.collectAsSharedFlowWithLifeCycle
import com.bosta.util.screen.sdp
import com.wagbat.utils.connectivity.ConnectivityObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AlbumScreen(
    modifier: Modifier,
    navController: NavHostController,
    connectivityStatus: StateFlow<ConnectivityObserver.Status>,
    albumId: String?,
    albumName: String?,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    var activePhotoUrl by rememberSaveable { mutableStateOf<String?>(null) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val connectionStatus = connectivityStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyGridState = rememberLazyGridState()
    val noInternetLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
    val logoAnimationState = animateLottieCompositionAsState(
        composition = noInternetLottie,
        isPlaying = true,
        speed = 0.5f
    )

    LaunchedEffect(key1 = true) {
        delay(1000)
        albumId?.let {
            viewModel.onEvent(AlbumIntents.UpdateAlbumId(albumId))
        }

        albumName?.let {
            viewModel.onEvent(AlbumIntents.UpdateAlbumName(albumName))
        }
    }





    viewModel.error.collectAsSharedFlowWithLifeCycle {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }


    if (activePhotoUrl != null && connectionStatus.value == ConnectivityObserver.Status.Available) {
        ImageDialog(
            albumImageUrl = activePhotoUrl,
        ) {
            activePhotoUrl = null
        }
    }



    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        if (connectionStatus.value == ConnectivityObserver.Status.Available) {

            SideEffect {
                viewModel.onEvent(AlbumIntents.GetAlbumPhotos)
            }

            albumName?.let {
                Header(
                    modifier = Modifier.align(CenterHorizontally),
                    albumName = it,
                    searchState = state.value.search
                ) { search ->
                    viewModel.onEvent(AlbumIntents.UpdateSearch(search))
                }
                Spacer(modifier = Modifier.size(15.sdp))
            }

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 70.sdp),
                state = lazyGridState
            ) {
                items(state.value.filteredPhotos, key = {
                    it.id
                }) { photo ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photo.thumbnailUrl)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.thumbnailImage),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clickable {
                                activePhotoUrl = photo.url
                            }
                    )
                }
            }


            if (state.value.photosModel == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                )
            }


        } else {


            LottieAnimation(
                noInternetLottie,
                modifier = Modifier
                    .size(100.sdp)
                    .align(CenterHorizontally),
                progress = { logoAnimationState.progress },
                contentScale = ContentScale.Crop,
            )
        }
    }
}
