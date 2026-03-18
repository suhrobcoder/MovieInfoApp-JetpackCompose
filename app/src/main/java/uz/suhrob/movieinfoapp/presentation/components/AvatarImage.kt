package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
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
            .build(),
        placeholder = painterResource(R.drawable.profile_placeholder),
        modifier = modifier.clip(CircleShape),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription
    )
}