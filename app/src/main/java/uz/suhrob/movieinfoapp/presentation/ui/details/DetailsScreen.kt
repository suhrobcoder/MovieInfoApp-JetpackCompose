package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.loadImage
import uz.suhrob.movieinfoapp.presentation.components.GenreRow

@Composable
fun DetailsScreen(movie: Movie) {
    Scaffold {
        ScrollableColumn(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.BottomCenter) {
                val image = loadImage(
                    url = movie.backdropPath,
                    defaultImage = R.drawable.backdrop_placeholder
                )
                image.value?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp).clip(
                            RoundedCornerShape(bottomLeft = 32.dp)
                        )
                    )
                }
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
                                    text = "${movie.voteAverage}/10", spanStyles = listOf(
                                        AnnotatedString.Range(SpanStyle(fontSize = 14.sp), 0, 4),
                                        AnnotatedString.Range(SpanStyle(fontSize = 12.sp), 3, 6)
                                    )
                                )
                            )
                            Text(
                                text = "${movie.voteCount}",
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
                        Column(
                            modifier = Modifier.weight(1f, fill = true).fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {}
                    }
                }
            }
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.h4.copy(fontSize = 32.sp, fontWeight = FontWeight.Medium))
                Row {
                    Text(text = movie.releaseDate)
                }
            }
            GenreRow(genres = movie.genres.map { Genre(0, it) })
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Overview", style = MaterialTheme.typography.h6)
                Text(text = movie.overview, style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface.copy(alpha = 0.6F)))
            }
        }
    }
}