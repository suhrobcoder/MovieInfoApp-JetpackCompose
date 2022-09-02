package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.getImageUrl
import kotlin.random.Random

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
        Text(text = movie.title, style = MaterialTheme.typography.h5)
    }
}

@Composable
fun SearchItemShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShimmerView(modifier = Modifier
            .height(height = 64.dp)
            .aspectRatio(2f / 3))
        ShimmerView(
            modifier = Modifier
                .padding(start = 16.dp)
                .height(18.dp)
                .fillMaxWidth(Random.nextInt(3, 6) / 10f)
        )
    }
}