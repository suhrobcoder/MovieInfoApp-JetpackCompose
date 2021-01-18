package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.Interaction
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.INITIAL
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.LIKED
import uz.suhrob.movieinfoapp.presentation.components.animations.heartSizeKey
import uz.suhrob.movieinfoapp.presentation.components.animations.transitionDefinition

@Composable
fun AnimatedLikeButton(state: LikeState, onClick: () -> Unit) {
    val transition = transition(
        definition = transitionDefinition,
        initState = state,
        toState = if (state == INITIAL) LIKED else INITIAL
    )
    Box(modifier = Modifier.preferredSize(48.dp), contentAlignment = Alignment.Center) {
        val interactionState = InteractionState()
        if (interactionState.contains(Interaction.Pressed)) {
            interactionState.removeInteraction(Interaction.Pressed)
        }
        Image(
            modifier = Modifier.clickable(onClick = onClick, interactionState = interactionState).size(transition[heartSizeKey]),
            bitmap = if (state == INITIAL) imageResource(id = R.drawable.heart)
            else imageResource(id = R.drawable.heart_filled)
        )
    }
}