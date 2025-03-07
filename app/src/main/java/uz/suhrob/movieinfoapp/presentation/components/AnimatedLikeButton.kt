package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.R

@Composable
fun AnimatedLikeButton(liked: Boolean, onClick: () -> Unit) {
    val transition = updateTransition(targetState = liked, label = "Like Transition")
    val heartSize = transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = 500
                64.dp at 100
                48.dp at 400
            }
        },
        targetValueByState = { 48.dp },
        label = "like",
    )
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier
                .clickable(
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
                .size(heartSize.value),
            bitmap = if (liked) ImageBitmap.imageResource(id = R.drawable.heart_filled)
            else ImageBitmap.imageResource(id = R.drawable.heart),
            contentDescription = "Like"
        )
    }
}