package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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