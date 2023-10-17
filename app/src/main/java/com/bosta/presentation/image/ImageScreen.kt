package com.bosta.presentation.image

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bosta.R
import kotlinx.coroutines.launch


@Composable
fun ImageDialog(
    albumImageUrl: String?,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true,

            ),
        content = {
            ImageScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray.copy(0.5f)),
                albumImageUrl = albumImageUrl
            ) {
                onDismiss.invoke()
            }
        })
}

@Composable
fun ImageScreen(
    modifier: Modifier,
    albumImageUrl: String?,
    onDismiss: () -> Unit,
) {

    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoomForGraphicLayer by rememberSaveable { mutableFloatStateOf(1f) }
    val zoom = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,
            albumImageUrl?.let { stringResource(R.string.please_check_this_beautiful_image, it) })
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Column(modifier = modifier.clickable {
        onDismiss.invoke()
    }, verticalArrangement = Arrangement.Center) {
        albumImageUrl?.let {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.thumbnailImage),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .clipToBounds()
                        .pointerInput(Unit) {

                            detectTapGestures(onDoubleTap = { clickedOffset ->
                                zoomForGraphicLayer = if (zoomForGraphicLayer > 1f) 1f else 2f
                                coroutineScope.launch {
                                    zoom.animateTo(if (zoom.value > 1f) 1f else 2f)
                                }
                                offset = calculateDoubleTapOffset(
                                    zoomForGraphicLayer,
                                    size,
                                    clickedOffset
                                )
                            },
                                onLongPress = { clickedOffset ->
                                    zoomForGraphicLayer = if (zoomForGraphicLayer > 1f) 1f else 2f
                                    coroutineScope.launch {
                                        zoom.animateTo(if (zoom.value > 1f) 1f else 2f)
                                    }
                                    offset = calculateDoubleTapOffset(
                                        zoomForGraphicLayer,
                                        size,
                                        clickedOffset
                                    )


                                }
                            )
                        }
                        .pointerInput(Unit) {
                            detectTransformGestures(
                                onGesture = { centroid, pan, gestureZoom, _ ->
                                    offset = offset.calculateNewOffset(
                                        centroid, pan, zoomForGraphicLayer, gestureZoom, size
                                    )
                                    coroutineScope.launch {
                                        zoom.animateTo(maxOf(1f, zoomForGraphicLayer * gestureZoom))
                                    }
                                }
                            )
                        }
                        .graphicsLayer {
                            translationX = -offset.x * zoom.value
                            translationY = -offset.y * zoom.value
                            scaleX = zoomForGraphicLayer
                            scaleY = zoomForGraphicLayer
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                        .aspectRatio(1f)

                )

                Icon(modifier = Modifier.clickable {
                    context.startActivity(shareIntent)
                }.padding(20.dp).align(Alignment.TopStart) ,imageVector = Icons.Default.Share, contentDescription = "share")
            }
        }


    }
}

fun Offset.calculateNewOffset(
    centroid: Offset,
    pan: Offset,
    zoom: Float,
    gestureZoom: Float,
    size: IntSize
): Offset {
    val newScale = maxOf(1f, zoom * gestureZoom)
    val newOffset = (this + centroid / zoom) -
            (centroid / newScale + pan / zoom)
    return Offset(
        newOffset.x.coerceIn(0f, (size.width / zoom) * (zoom - 1f)),
        newOffset.y.coerceIn(0f, (size.height / zoom) * (zoom - 1f))
    )
}

fun calculateDoubleTapOffset(
    zoom: Float,
    size: IntSize,
    tapOffset: Offset
): Offset {
    val newOffset = Offset(tapOffset.x, tapOffset.y)
    return Offset(
        newOffset.x.coerceIn(0f, (size.width / zoom) * (zoom - 1.5f).coerceAtLeast(0f)),
        newOffset.y.coerceIn(0f, (size.height / zoom) * (zoom - 1.5f).coerceAtLeast(0f))
    )
}
