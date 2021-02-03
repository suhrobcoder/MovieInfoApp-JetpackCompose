package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.other.loadImage

@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null
) {
    val image by loadImage(
        url = path,
        defaultImage = R.drawable.profile_placeholder
    )
    image?.let {
        Image(
            bitmap = it.asImageBitmap(),
            modifier = modifier.clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = contentDescription
        )
    }
}