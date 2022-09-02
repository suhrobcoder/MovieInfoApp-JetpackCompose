package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.other.getImageUrl

@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(getImageUrl(path))
            .placeholder(R.drawable.profile_placeholder)
            .build(),
        modifier = modifier.clip(CircleShape),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription
    )
}