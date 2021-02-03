package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.presentation.components.LikeState.INITIAL

enum class LikeState {
    INITIAL, LIKED
}

@Composable
fun AnimatedLikeButton(state: LikeState, onClick: () -> Unit) {
    val transition = updateTransition(targetState = state, label = "Like Transition")
    val heartSize = transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = 500
                64.dp at 100
                48.dp at 400
            }
        },
        targetValueByState = { 48.dp }
    )
    Box(modifier = Modifier.preferredSize(48.dp), contentAlignment = Alignment.Center) {
        val interactionState = InteractionState()
        Image(
            modifier = Modifier.clickable(onClick = onClick, interactionState = interactionState, indication = null).size(heartSize.value),
            bitmap = if (state == INITIAL) imageResource(id = R.drawable.heart)
            else imageResource(id = R.drawable.heart_filled),
            contentDescription = "Like"
        )
    }
}