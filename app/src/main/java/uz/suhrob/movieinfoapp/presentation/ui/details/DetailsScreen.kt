package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.formatTime
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.presentation.components.*

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun DetailsScreen(viewModel: DetailsViewModel, navController: NavController) {
    val movieRes = viewModel.movie.collectAsState()
    val showDialog = viewModel.isShowingDialog.collectAsState()
    val composeScope = rememberCoroutineScope()
    val snackBarController = MovieSnackBarController(composeScope)
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = viewModel.movieId) {
        viewModel.snackBarFlow.collect {
            snackBarController.showSnackBar(scaffoldState, it)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
        modifier = Modifier.navigationBarsPadding()
    ) { paddingValues ->
        when (movieRes.value) {
            is Resource.Success -> {
                val movie = movieRes.value.data!!
                if (movie.video) {
                    viewModel.onTriggerEvent(DetailsEvent.LoadVideos)
                }
                viewModel.onTriggerEvent(DetailsEvent.LoadReviews)

                if (showDialog.value) {
                    val rating = viewModel.rating.collectAsState()
                    RatingDialog(
                        rating = rating.value,
                        onChange = { viewModel.setRating(it) },
                        onSubmit = { viewModel.onTriggerEvent(DetailsEvent.SubmitRating(rating.value)) },
                        onClose = { viewModel.onTriggerEvent(DetailsEvent.CloseDialog) })
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        val likeState = viewModel.likeState.collectAsState()
                        Box(contentAlignment = Alignment.BottomCenter) {
                            BackdropImage(backdropPath = movie.backdropPath)
                            RatingBar(
                                voteCount = movie.voteCount,
                                voteAverage = movie.voteAverage,
                                likeState = likeState.value,
                                showDialog = { viewModel.onTriggerEvent(DetailsEvent.ShowDialog) },
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
                        val cast = viewModel.cast.collectAsState()
                        if (cast.value.isNotEmpty()) {
                            CastList(list = cast.value)
                        }
                        val videos = viewModel.videos.collectAsState()
                        if (videos.value.isNotEmpty()) {
                            VideosColumn(videos = videos.value) {}
                        }
                        val reviews = viewModel.reviews.collectAsState()
                        if (reviews.value.isNotEmpty()) {
                            ReviewsColumn(reviews = reviews.value)
                        }
                    }
                    DetailsAppBar(transparent = scrollState.value < 300, title = movie.title) { navController.popBackStack() }
                    MovieSnackBar(
                        scaffoldState = scaffoldState,
                        onClickAction = {},
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(all = 4.dp)
                    )
                }

            }
            is Resource.Loading -> {
                Column(
                    modifier = Modifier.padding(paddingValues),
                ) {
                    DetailsAppBar(
                        transparent = false,
                        title = "",
                        onNavigationClick = { navController.popBackStack() })
                    Loading()
                }
            }
            is Resource.Error -> {
                Column(
                    modifier = Modifier.padding(paddingValues),
                ) {
                    DetailsAppBar(
                        transparent = false,
                        title = "",
                        onNavigationClick = { navController.popBackStack() })
                    Error(
                        modifier = Modifier.statusBarsPadding(),
                        onRetry = { viewModel.onTriggerEvent(DetailsEvent.LoadMovie) }
                    )
                }
            }
        }
    }
}

@Composable
fun BackdropImage(backdropPath: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(getImageUrl(backdropPath))
            .placeholder(R.drawable.backdrop_placeholder)
            .build(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .aspectRatio(16f / 9)
            .clip(
                RoundedCornerShape(bottomStart = 32.dp)
            ),
        contentScale = ContentScale.Crop,
        contentDescription = "Backdrop image"
    )
}

@Composable
fun RatingBar(
    voteCount: Int,
    voteAverage: Double,
    likeState: LikeState,
    showDialog: () -> Unit,
    onLikeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp)
            .height(72.dp),
        shape = RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50),
        elevation = 8.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = Color.Yellow,
                    contentDescription = null
                )
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
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxHeight()
                    .clickable(onClick = showDialog),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null)
                Text(text = "Rate This")
            }
            Box(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxHeight(),
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
            Spacer(modifier = Modifier.width(16.dp))
            runtime?.let { Text(text = formatTime(it)) }
        }
    }
}

@Composable
fun MovieOverview(overview: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Overview", style = MaterialTheme.typography.h5)
        Text(
            text = overview,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6F)
            )
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun DetailsAppBar(transparent: Boolean = true, title: String, onNavigationClick: () -> Unit) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    ) {
        BoxWithConstraints {
            val primaryColor = MaterialTheme.colors.primary
            val radius = animateFloatAsState(
                targetValue = if (transparent) 0f else with(LocalDensity.current) { maxWidth.toPx() }
            )
            Canvas(modifier = Modifier.height(maxHeight)) {
                drawCircle(color = primaryColor, radius.value, Offset(28.dp.toPx(), (maxHeight-28.dp).toPx()))
            }
            Row(modifier = Modifier.statusBarsPadding(), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(MaterialTheme.colors.primary)
                        .clickable(onClick = onNavigationClick)
                        .padding(12.dp),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null
                )
                AnimatedVisibility(visible = !transparent) {
                    Text(text = title, style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onPrimary))
                }
            }
        }
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