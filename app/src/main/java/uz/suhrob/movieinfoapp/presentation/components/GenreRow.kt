package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer
import uz.suhrob.movieinfoapp.domain.model.Genre

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreRow(
    genres: List<Genre>,
    selectedGenre: Genre = Genre(-1, ""),
    error: Boolean = false,
    shimmer: Shimmer? = null,
    onGenreSelected: (Genre) -> Unit = {}
) {
    if (!error) {
        if (genres.isEmpty()) {
            GenreShimmer(shimmer)
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                itemsIndexed(genres) { index, genre ->
                    FilterChip(
                        selected = selectedGenre == genre,
                        onClick = { onGenreSelected(genre) },
                        label = { Text(text = genre.name) },
                        modifier = Modifier.padding(
                            start = if (index == 0) 16.dp else 6.dp,
                            end = if (index == genres.size - 1) 16.dp else 6.dp
                        ),
                        shape = RoundedCornerShape(percent = 50),
                    )
                }
            }
        }
    }
}

@Composable
fun GenreItem(genre: Genre, modifier: Modifier, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .clickable(onClick = onClick)
            .border(
                width = 2.dp,
                color = Color.Black.copy(alpha = 0.1F),
                shape = RoundedCornerShape(percent = 50)
            )
            .background(if (selected) Color.Black.copy(alpha = 0.1F) else Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre.name,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun GenreShimmer(shimmer: Shimmer?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .shimmer(shimmer)
                    .size(width = 128.dp, height = 36.dp)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(Color.Gray),
            )
        }
    }
}