package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientDensity

@Composable
fun ShimmerView(
    modifier: Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val widthPx = with(AmbientDensity.current) {
            maxWidth.toPx()
        }
        val heightPx = with(AmbientDensity.current) {
            maxHeight.toPx()
        }
        val colors = listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.5f),
        )
        val gradientWidth = widthPx * 0.2f
        val infiniteTransition = rememberInfiniteTransition()
        val xShimmer = infiniteTransition.animateFloat(
            initialValue = -gradientWidth,
            targetValue = widthPx + gradientWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )
        val yShimmer = infiniteTransition.animateFloat(
            initialValue = -gradientWidth,
            targetValue = heightPx + gradientWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )
        val shimmerBrush = Brush.linearGradient(
            colors,
            start = Offset(xShimmer.value - gradientWidth, yShimmer.value - gradientWidth),
            end = Offset(xShimmer.value + gradientWidth, yShimmer.value + gradientWidth)
        )
        Surface(modifier = Modifier.height(maxHeight).width(maxWidth)) {
            Spacer(modifier = Modifier.fillMaxSize().background(brush = shimmerBrush))
        }
    }
}