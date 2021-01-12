package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.suhrob.movieinfoapp.domain.model.Genre

@Composable
fun GenreRow(genres: List<Genre>, selectedGenre: Genre = Genre(-1, ""), onGenreSelected: (Genre) -> Unit = {}) {
    ScrollableRow(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        genres.forEachIndexed { index, genre ->
            GenreItem(
                genre = genre,
                Modifier.padding(
                    start = if (index == 0) 16.dp else 6.dp,
                    end = if (index == genres.size - 1) 16.dp else 6.dp
                ),
                selected = selectedGenre == genre
            ) {
                onGenreSelected(genre)
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
            .padding(horizontal = 20.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = genre.name, style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
    }
}