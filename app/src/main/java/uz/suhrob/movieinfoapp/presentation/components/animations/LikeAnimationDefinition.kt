package uz.suhrob.movieinfoapp.presentation.components.animations

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.INITIAL
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.LIKED

enum class LikeState {
    INITIAL, LIKED
}

val heartSizeKey = DpPropKey("heart_size")

val transitionDefinition = transitionDefinition<LikeState> {
    state(INITIAL) { this[heartSizeKey] = 48.dp }
    state(LIKED) { this[heartSizeKey] = 48.dp }
    transition(INITIAL to LIKED) {
        heartSizeKey using keyframes {
            durationMillis = 500
            64.dp at 100
            48.dp at 400
        }
    }
    transition(LIKED to INITIAL) {
        heartSizeKey using keyframes {
            durationMillis = 500
            64.dp at 100
            48.dp at 400
        }
    }
}