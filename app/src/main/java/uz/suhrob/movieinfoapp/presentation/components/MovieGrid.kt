package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.getImageUrl

@ExperimentalFoundationApi
@Composable
fun MovieGrid(
    movies: List<Movie>,
    onChangeScrollPosition: (Int) -> Unit,
    onLastItemCreated: () -> Unit,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        itemsIndexed(movies) { index, movie ->
            MovieItem(
                title = movie.title,
                imageUrl = getImageUrl(movie.posterPath),
                rating = movie.voteAverage,
                onClick = { onMovieClicked(movie) }
            )
            if (index + 1 == movies.size) {
                onLastItemCreated()
            }
            onChangeScrollPosition(index)
        }
    }
}

@Composable
fun MovieItem(title: String, imageUrl: String, rating: Double, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(getImageUrl(imageUrl))
                .placeholder(R.drawable.poster_placeholder)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3)
                .clip(RoundedCornerShape(32.dp))
                .clickable(onClick = onClick),
            contentScale = ContentScale.Crop,
            contentDescription = "$title movie poster"
        )
        Text(text = title, maxLines = 1, style = MaterialTheme.typography.titleLarge)
        Row {
            Icon(
                imageVector = Icons.Filled.Star,
                tint = Color.Yellow,
                contentDescription = "Star icon"
            )
            Text(text = rating.toString(), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun MovieItemShimmer(shimmer: Shimmer) {
    Column(modifier = Modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
                .shimmer(shimmer)
                .fillMaxWidth()
                .aspectRatio(2f / 3)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.Gray)
        )
        Box(
            modifier = Modifier
                .shimmer(shimmer)
                .padding(vertical = 4.dp)
                .height(24.dp)
                .fillMaxWidth(0.9f)
                .background(Color.Gray)
        )
        Box(
            modifier = Modifier
                .shimmer(shimmer)
                .height(24.dp)
                .fillMaxWidth(0.4f)
                .background(Color.Gray)
        )
    }
}