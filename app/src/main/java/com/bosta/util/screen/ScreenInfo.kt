package com.wagbat.utils.screen

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowLayoutInfo
import com.bosta.util.screen.Dimension
import com.bosta.util.screen.ScreenClassifier
import com.bosta.util.screen.WindowSizeClass


class ScreenInfo {
    fun screenClassifier(windowDpSize: DpSize, devicePosture: WindowLayoutInfo) : ScreenClassifier
    {
        val foldingFeature = devicePosture.displayFeatures.find {
            it is FoldingFeature
        } as? FoldingFeature

        return when {
            foldingFeature == null -> {
                createFullyOpenedDevice(windowDpSize)
            }
            isBookMode(foldingFeature) -> {
                createBookModeObject(foldingFeature)
            }
            isTableTopMode(foldingFeature) -> {
                createTableTopModeObject(foldingFeature)
            }
            else -> {
                createFullyOpenedDevice(windowDpSize)
            }
        }
    }


    fun  createFullyOpenedDevice(windowDpSize: DpSize) : ScreenClassifier {
        val windowHeightSizeClass = when
        {
            windowDpSize.height < 480.dp -> WindowSizeClass.Compact
            windowDpSize.height < 900.dp -> WindowSizeClass.Medium
            else  -> WindowSizeClass.Expanded
        }


        val windowWidthSizeClass = when
        {
            windowDpSize.width < 480.dp -> WindowSizeClass.Compact
            windowDpSize.width < 900.dp -> WindowSizeClass.Medium
            else  -> WindowSizeClass.Expanded
        }

        return ScreenClassifier.FullyOpened(
            height = Dimension(windowDpSize.height, windowHeightSizeClass),
            width = Dimension(windowDpSize.width, windowWidthSizeClass)
        )
    }

    private fun createBookModeObject(foldingFeature: FoldingFeature) : ScreenClassifier.HalfOpened.BookMode
    {
        val screenWidth = foldingFeature.bounds.left + foldingFeature.bounds.right
        val ratio = foldingFeature.bounds.left.toFloat() / screenWidth.toFloat()
        return ScreenClassifier.HalfOpened.BookMode(
            hingePosition = foldingFeature.bounds,
            isSeparating = foldingFeature.isSeparating,
            hingeSeparationRatio = ratio,
            occlusionType = foldingFeature.occlusionType
        )
    }

    private fun createTableTopModeObject(foldingFeature: FoldingFeature) : ScreenClassifier.HalfOpened.TableTopMode
    {
        val screenWidth = foldingFeature.bounds.bottom + foldingFeature.bounds.top
        val ratio = foldingFeature.bounds.top.toFloat() / screenWidth.toFloat()
        return ScreenClassifier.HalfOpened.TableTopMode(
            hingePosition = foldingFeature.bounds,
            isSeparating = foldingFeature.isSeparating,
            hingeSeparationRatio = ratio,
            occlusionType = foldingFeature.occlusionType
        )
    }


    fun isBookMode(foldingFeature: FoldingFeature) =
        foldingFeature.state == FoldingFeature.State.HALF_OPENED && foldingFeature.orientation == FoldingFeature.Orientation.VERTICAL

    fun isTableTopMode(foldingFeature: FoldingFeature) =
        foldingFeature.state == FoldingFeature.State.HALF_OPENED && foldingFeature.orientation == FoldingFeature.Orientation.HORIZONTAL

}