package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
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

class DetailsScreen(private val movieId: Int) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @ExperimentalAnimationApi
    @ExperimentalCoroutinesApi
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<DetailsScreenModel>()
        screenModel.movieId = movieId
        val movieRes = screenModel.movie.collectAsState()
        val showDialog = screenModel.isShowingDialog.collectAsState()
        val scrollState = rememberScrollState()
        val snackBarHostState = SnackbarHostState()
        LaunchedEffect(key1 = screenModel.movieId) {
            screenModel.snackBarFlow.collect {
                snackBarHostState.showSnackbar(it)
            }
        }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
        ) { paddingValues ->
            when (movieRes.value) {
                is Resource.Success -> {
                    val movie = movieRes.value.data!!
                    if (movie.video) {
                        screenModel.onTriggerEvent(DetailsEvent.LoadVideos)
                    }
                    screenModel.onTriggerEvent(DetailsEvent.LoadReviews)

                    if (showDialog.value) {
                        val rating = screenModel.rating.collectAsState()
                        RatingDialog(
                            rating = rating.value,
                            onChange = { screenModel.setRating(it) },
                            onSubmit = { screenModel.onTriggerEvent(DetailsEvent.SubmitRating(rating.value)) },
                            onClose = { screenModel.onTriggerEvent(DetailsEvent.CloseDialog) })
                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            val likeState = screenModel.likeState.collectAsState()
                            Box(contentAlignment = Alignment.BottomCenter) {
                                BackdropImage(backdropPath = movie.backdropPath)
                                RatingBar(
                                    voteCount = movie.voteCount,
                                    voteAverage = movie.voteAverage,
                                    likeState = likeState.value,
                                    showDialog = { screenModel.onTriggerEvent(DetailsEvent.ShowDialog) },
                                    onLikeClick = {
                                        screenModel.onTriggerEvent(DetailsEvent.LikeClick)
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
                            val cast = screenModel.cast.collectAsState()
                            if (cast.value.isNotEmpty()) {
                                CastList(list = cast.value)
                            }
                            val videos = screenModel.videos.collectAsState()
                            if (videos.value.isNotEmpty()) {
                                VideosColumn(videos = videos.value) {}
                            }
                            val reviews = screenModel.reviews.collectAsState()
                            if (reviews.value.isNotEmpty()) {
                                ReviewsColumn(reviews = reviews.value)
                            }
                        }
                        DetailsAppBar(transparent = scrollState.value < 300, title = movie.title) { navigator.pop() }
                    }

                }
                is Resource.Loading -> {
                    Column(
                        modifier = Modifier.padding(paddingValues),
                    ) {
                        DetailsAppBar(
                            transparent = false,
                            title = "",
                            onNavigationClick = { navigator.pop() })
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
                            onNavigationClick = { navigator.pop() })
                        Error(
                            modifier = Modifier.statusBarsPadding(),
                            onRetry = { screenModel.onTriggerEvent(DetailsEvent.LoadMovie) }
                        )
                    }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                    style = MaterialTheme.typography.bodySmall,
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
            style = MaterialTheme.typography.titleLarge.copy(
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
        Text(text = "Overview", style = MaterialTheme.typography.titleLarge)
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6F)
            )
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun DetailsAppBar(transparent: Boolean = true, title: String, onNavigationClick: () -> Unit) {
//    TopAppBar(
//        backgroundColor = Color.Transparent,
//        elevation = 0.dp,
//    ) {
//        BoxWithConstraints {
//            val primaryColor = MaterialTheme.colorScheme.primary
//            val radius = animateFloatAsState(
//                targetValue = if (transparent) 0f else with(LocalDensity.current) { maxWidth.toPx() }
//            )
//            Canvas(modifier = Modifier.height(maxHeight)) {
//                drawCircle(color = primaryColor, radius.value, Offset(28.dp.toPx(), (maxHeight-28.dp).toPx()))
//            }
//            Row(modifier = Modifier.statusBarsPadding(), verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Filled.ArrowBack,
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .clip(RoundedCornerShape(percent = 50))
//                        .background(MaterialTheme.colorScheme.primary)
//                        .clickable(onClick = onNavigationClick)
//                        .padding(12.dp),
//                    tint = MaterialTheme.colorScheme.onPrimary,
//                    contentDescription = null
//                )
//                AnimatedVisibility(visible = !transparent) {
//                    Text(text = title, style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary))
//                }
//            }
//        }
//    }
}

@Composable
fun VideosColumn(videos: List<Video>, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {
        Text(text = "Reviews", style = MaterialTheme.typography.titleLarge)
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
        Text(text = "Reviews", style = MaterialTheme.typography.titleLarge)
        reviews.forEach { review ->
            ReviewItem(
                authorName = review.authorName,
                avatarUrl = review.avatarPath,
                content = review.content
            )
        }
    }
}