package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.animation.transition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import uz.suhrob.movieinfoapp.presentation.components.animations.ShimmerAnimationDefinition
import uz.suhrob.movieinfoapp.presentation.components.animations.ShimmerAnimationDefinition.ShimmerAnimationState.END
import uz.suhrob.movieinfoapp.presentation.components.animations.ShimmerAnimationDefinition.ShimmerAnimationState.START

@Composable
fun ShimmerView(
    modifier: Modifier
) {
    WithConstraints(modifier = modifier) {
        val widthPx = with(AmbientDensity.current) {
            maxWidth.toPx()
        }
        val heightPx = with(AmbientDensity.current) {
            maxHeight.toPx()
        }
        val shimmerAnimationDefinition = remember {
            ShimmerAnimationDefinition(
                widthPx = widthPx,
                heightPx = heightPx
            )
        }
        val shimmerAnimation = transition(
            definition = shimmerAnimationDefinition.shimmerAnimationDefinition,
            initState = START,
            toState = END
        )
        val colors = listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.5f),
        )
        val gradientWidth = shimmerAnimationDefinition.gradientWidth
        val xShimmer = shimmerAnimation[shimmerAnimationDefinition.xPropKey]
        val yShimmer = shimmerAnimation[shimmerAnimationDefinition.yPropKey]
        val shimmerBrush = Brush.linearGradient(
            colors,
            start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
            end = Offset(xShimmer + gradientWidth, yShimmer + gradientWidth)
        )
        Surface(modifier = Modifier.height(maxHeight).width(maxWidth)) {
            Spacer(modifier = Modifier.fillMaxSize().background(brush = shimmerBrush))
        }
    }
}