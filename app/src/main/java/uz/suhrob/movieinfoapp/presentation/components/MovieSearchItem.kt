package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.other.loadImage
import kotlin.random.Random

@Composable
fun MovieSearchItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .preferredHeight(64.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val image by loadImage(
            url = getImageUrl(movie.posterPath),
            defaultImage = R.drawable.poster_placeholder
        )
        image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = Modifier
                    .preferredSize(size = 64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = movie.title, style = MaterialTheme.typography.h5)
    }
}

@Composable
fun SearchItemShimmer() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .preferredHeight(64.dp)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShimmerView(modifier = Modifier.preferredHeight(height = 64.dp).aspectRatio(2f/3))
        ShimmerView(modifier = Modifier.padding(start = 16.dp).preferredHeight(18.dp).fillMaxWidth(
            Random.nextInt(3, 6) / 10f))
    }
}