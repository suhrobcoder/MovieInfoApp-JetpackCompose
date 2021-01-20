package uz.suhrob.movieinfoapp.presentation.ui.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.formatTime
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.other.loadImage
import uz.suhrob.movieinfoapp.presentation.components.*
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, navController: NavController) {
    when (val data = viewModel.movie.value) {
        is Resource.Success -> {
            val movie = data.data!!
            Log.d("AppDebug", "Details: ${movie.video}")
            if (movie.video) {
                viewModel.onTriggerEvent(DetailsEvent.LoadVideos)
            }
            viewModel.onTriggerEvent(DetailsEvent.LoadReviews)
            Scaffold {
                Box(modifier = Modifier.fillMaxSize()) {
                    ScrollableColumn(modifier = Modifier.fillMaxSize()) {
                        Box(contentAlignment = Alignment.BottomCenter) {
                            BackdropImage(backdropPath = movie.backdropPath)
                            RatingBar(
                                voteCount = movie.voteCount,
                                voteAverage = movie.voteAverage,
                                likeState = viewModel.likeState.value,
                                onLikeClick = {
                                    viewModel.onTriggerEvent(DetailsEvent.LikeClick)
                                }
                            )
                        }
                        DetailsHeader(
                            title = movie.title,
                            movieReleaseYear = movie.getMovieReleaseYear(),
                            runtime = movie.runtime
                        )
                        GenreRow(genres = movie.genres)
                        MovieOverview(overview = movie.overview)
                        val videos = viewModel.videos.value
                        if (videos.isNotEmpty()) {
                            VideosColumn(videos = videos) {}
                        }
                        val reviews = viewModel.reviews.value
                        if (reviews.isNotEmpty()) {
                            ReviewsColumn(reviews = reviews)
                        }
                    }
                    DetailsAppBar { navController.popBackStack() }
                }
            }
        }
        is Resource.Loading -> {
            Loading()
        }
        is Resource.Error -> {
            Error(onRetry = { viewModel.onTriggerEvent(DetailsEvent.LoadMovie) })
            Log.d("AppDebug", "Details: ${data.message}")
        }
    }
}

@Composable
fun BackdropImage(backdropPath: String) {
    val image = loadImage(
        url = getImageUrl(backdropPath),
        defaultImage = R.drawable.backdrop_placeholder
    )
    image.value?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp).clip(
                RoundedCornerShape(bottomLeft = 32.dp)
            ),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun RatingBar(
    voteCount: Int,
    voteAverage: Double,
    likeState: LikeState,
    onLikeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp)
            .height(72.dp),
        shape = RoundedCornerShape(topLeftPercent = 50, bottomLeftPercent = 50),
        elevation = 8.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f, fill = true).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Filled.Star, tint = Color.Yellow)
                Text(
                    text = AnnotatedString(
                        text = "${voteAverage}/10", spanStyles = listOf(
                            AnnotatedString.Range(SpanStyle(fontSize = 14.sp), 0, 4),
                            AnnotatedString.Range(SpanStyle(fontSize = 12.sp), 3, 6)
                        )
                    )
                )
                Text(
                    text = "$voteCount",
                    style = MaterialTheme.typography.caption
                )
            }
            Column(
                modifier = Modifier.weight(1f, fill = true).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Outlined.Star)
                Text(text = "Rate This")
            }
            Box(
                modifier = Modifier.weight(1f, fill = true).fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedLikeButton(state = likeState, onClick = onLikeClick)
            }
        }
    }
}

@Composable
fun DetailsHeader(title: String, movieReleaseYear: String, runtime: Int?) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Row {
            Text(text = movieReleaseYear)
            runtime?.let { Text(text = formatTime(it)) }
        }
    }
}

@Composable
fun MovieOverview(overview: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Overview", style = MaterialTheme.typography.h6)
        Text(
            text = overview,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6F)
            )
        )
    }
}

@Composable
fun DetailsAppBar(onNavigationClick: () -> Unit) {
    TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(percent = 50))
                .clickable(onClick = onNavigationClick)
                .padding(8.dp)
        )
    }
}

@Composable
fun VideosColumn(videos: List<Video>, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {
        Text(text = "Reviews", style = MaterialTheme.typography.h5)
        Column {
            videos.forEach { video ->
                VideoItem(name = video.name, onClick = onClick)
            }
        }
    }
}

@Composable
fun ReviewsColumn(reviews: List<Review>) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {
        Text(text = "Reviews", style = MaterialTheme.typography.h5)
        reviews.forEach { review ->
            ReviewItem(
                authorName = review.authorName,
                avatarUrl = review.avatarPath,
                content = review.content
            )
        }
    }
}