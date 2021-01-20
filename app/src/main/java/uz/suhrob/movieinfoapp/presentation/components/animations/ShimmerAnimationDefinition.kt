package uz.suhrob.movieinfoapp.presentation.components.animations

import androidx.compose.animation.core.*
import uz.suhrob.movieinfoapp.presentation.components.animations.ShimmerAnimationDefinition.ShimmerAnimationState.*

class ShimmerAnimationDefinition(
    private val widthPx: Float,
    private val heightPx: Float
) {
    enum class ShimmerAnimationState {
        START, END
    }

    val gradientWidth = widthPx * 0.2f

    val xPropKey = FloatPropKey("xPropKey")
    val yPropKey = FloatPropKey("yPropKey")
    val shimmerAnimationDefinition = transitionDefinition<ShimmerAnimationState> {
        state(START) {
            this[xPropKey] = -gradientWidth
            this[yPropKey] = -gradientWidth
        }
        state(END) {
            this[xPropKey] = widthPx + gradientWidth
            this[yPropKey] = heightPx + gradientWidth
        }
        transition(START, END) {
            xPropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    delayMillis = 300,
                    easing = LinearEasing
                )
            )
            yPropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    delayMillis = 300,
                    easing = LinearEasing
                )
            )
        }
    }
}