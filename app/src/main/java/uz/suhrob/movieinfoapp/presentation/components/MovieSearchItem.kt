package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlin.random.Random
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.getImageUrl

@Composable
fun MovieSearchItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(getImageUrl(movie.posterPath))
                .placeholder(R.drawable.poster_placeholder)
                .build(),
            modifier = Modifier
                .size(size = 64.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "${movie.title} poster"
        )
        Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun SearchItemShimmer() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .shimmer(shimmer)
                .height(height = 64.dp)
                .width(42.dp)
                .background(Color.Gray)
        )
        Box(
            modifier = Modifier
                .shimmer(shimmer)
                .padding(start = 16.dp)
                .height(18.dp)
                .fillMaxWidth(Random.nextInt(3, 6) / 10f)
                .background(Color.Gray)
        )
    }
}