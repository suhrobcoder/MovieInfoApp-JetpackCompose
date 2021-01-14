package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.other.loadImage
import uz.suhrob.movieinfoapp.presentation.components.*

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, navController: NavController) {
    when (val data = viewModel.movie.value) {
        is Resource.Success -> {
            val movie = data.data!!
            if (movie.video) {
                viewModel.loadVideos(movie.id)
            }
            viewModel.loadReviews(movie.id)
            Scaffold {
                DetailsAppBar {navController.popBackStack()}
                ScrollableColumn(modifier = Modifier.fillMaxSize()) {
                    Box(contentAlignment = Alignment.BottomCenter) {
                        val image = loadImage(
                            url = getImageUrl(movie.backdropPath),
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
                        RatingBar(voteCount = movie.voteCount, voteAverage = movie.voteAverage)
                    }
                    Column(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 24.dp,
                            bottom = 8.dp
                        )
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.h4.copy(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Row {
                            movie.releaseDate?.let { Text(text = movie.releaseDate) }
                        }
                    }
                    GenreRow(genres = movie.genres)
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Overview", style = MaterialTheme.typography.h6)
                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.body1.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6F)
                            )
                        )
                        val videos = viewModel.videos.value
                        if (videos.isNotEmpty()) {
                            VideosColumn(videos = videos) {}
                        }
                        val reviews = viewModel.reviews.value
                        if (reviews.isNotEmpty()) {
                            ReviewsColumn(reviews = reviews)
                        }
                    }
                }
            }
        }
        is Resource.Loading -> {
            Loading()
        }
        is Resource.Error -> {
            Error(onRetry = { /*TODO*/ })
        }
    }
}

@Composable
fun RatingBar(
    voteCount: Int,
    voteAverage: Double
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
        }
    }
}

@Composable
fun DetailsAppBar(onNavigationClick: () -> Unit) {
    TopAppBar {
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
        LazyColumn {
            items(items = videos) { video ->
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
        LazyColumn {
            items(items = reviews) { review ->
                ReviewItem(
                    authorName = review.authorName,
                    avatarUrl = review.avatarPath,
                    content = review.content
                )
            }
        }
    }
}