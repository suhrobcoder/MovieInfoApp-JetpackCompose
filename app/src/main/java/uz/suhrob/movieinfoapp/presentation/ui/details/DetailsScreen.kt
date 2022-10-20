package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Cast
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.formatTime
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.presentation.components.*
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsEvent.*
import kotlin.math.roundToInt

private val headerHeight = 275.dp
private val toolbarHeight = 56.dp

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun DetailsScreen(component: Details) {
    val state by component.state.subscribeAsState()
    val dialogState by component.dialogState.subscribeAsState()
    val scrollState = rememberScrollState()
    val snackBarHostState = SnackbarHostState()
    LaunchedEffect(key1 = state.movie.id) {
        component.snackBarFlow.collect {
            snackBarHostState.showSnackbar(it)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { paddingValues ->
        if (dialogState.show) {
            RatingDialog(
                rating = dialogState.rating,
                onChange = { component.sendEvent(SetRating(it)) },
                onSubmit = { component.sendEvent(SubmitRating) },
                onClose = { component.sendEvent(CloseDialog) })
        }
        val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
        val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            DetailsBody(
                movie = state.movie,
                cast = state.cast,
                videos = state.videos,
                reviews = state.reviews,
                scroll = scrollState
            )
            DetailsHeader(
                state.movie,
                state.liked,
                scrollState.value.toFloat(),
                headerHeightPx,
                showDialog = {
                    component.sendEvent(ShowDialog)
                },
                onLikeClick = {
                    component.sendEvent(LikeClick)
                },
            )
            Toolbar(
                scrollState,
                state.movie.title,
                { component.sendEvent(NavigateBack) },
                headerHeightPx,
                toolbarHeightPx
            )
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
    liked: Boolean,
    showDialog: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
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
                        text = "${(voteAverage * 10).roundToInt() / 10f}/10", spanStyles = listOf(
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
                AnimatedLikeButton(liked, onClick = onLikeClick)
            }
        }
    }
}

@Composable
fun DetailsHeader(
    movie: Movie,
    liked: Boolean,
    scroll: Float,
    headerHeightPx: Float,
    showDialog: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                translationY = -scroll / 2f
                alpha = (-1f / headerHeightPx) * scroll + 1
            }
    ) {
        BackdropImage(movie.backdropPath)

        RatingBar(
            voteCount = movie.voteCount,
            voteAverage = movie.voteAverage,
            liked = liked,
            showDialog = showDialog,
            onLikeClick = onLikeClick,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

@Composable
fun DetailsBody(
    movie: Movie,
    cast: List<Cast>,
    videos: List<Video>,
    reviews: List<Review>,
    scroll: ScrollState,
) {
    Column(
        modifier = Modifier.verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Row {
                Text(text = movie.getMovieReleaseYear())
                Spacer(modifier = Modifier.width(16.dp))
                movie.runtime?.let { Text(text = formatTime(it)) }
            }
        }
        GenreRow(genres = movie.genres)
        MovieOverview(overview = movie.overview)
        if (cast.isNotEmpty()) {
            CastList(list = cast)
        }
        if (videos.isNotEmpty()) {
            VideosColumn(videos = videos) {}
        }
        if (reviews.isNotEmpty()) {
            ReviewsColumn(reviews = reviews)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    scroll: ScrollState,
    title: String,
    navigateBack: () -> Unit,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember { derivedStateOf { scroll.value >= toolbarBottom } }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)),
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back Button",
                    )
                }
            },
            title = { Text(title) },
        )
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

@Composable
fun VideosColumn(videos: List<Video>, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {
        Text(text = "Videos", style = MaterialTheme.typography.titleLarge)
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