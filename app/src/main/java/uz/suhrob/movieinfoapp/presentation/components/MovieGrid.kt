package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.other.loadImage

@ExperimentalFoundationApi
@Composable
fun MovieGrid(movies: List<Movie>, onMovieClicked: (Movie) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(
                title = movie.title,
                imageUrl = getImageUrl(movie.posterPath),
                rating = movie.voteAverage,
                onClick = { onMovieClicked(movie) }
            )
        }
    }
}

@Composable
fun MovieItem(title: String, imageUrl: String, rating: Double, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        val image by loadImage(
            url = imageUrl,
            defaultImage = R.drawable.poster_placeholder
        )
        image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .clickable(onClick = onClick),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = title, maxLines = 1, style = MaterialTheme.typography.h6)
        Row {
            Icon(imageVector = Icons.Filled.Star, tint = Color.Yellow)
            Text(text = rating.toString(), style = MaterialTheme.typography.subtitle1)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MovieShimmerGrid() {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(listOf(1, 2, 3, 4, 5, 6)) {
            Column(modifier = Modifier.padding(8.dp)) {
                ShimmerView(
                    modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3)
                        .clip(RoundedCornerShape(32.dp))
                )
                ShimmerView(modifier = Modifier.padding(vertical = 4.dp).height(24.dp).fillMaxWidth(0.9f))
                ShimmerView(modifier = Modifier.height(24.dp).fillMaxWidth(0.4f))
            }
        }
    }
}