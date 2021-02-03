package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.domain.model.Cast
import uz.suhrob.movieinfoapp.other.getImageUrl

@Composable
fun CastList(
    modifier: Modifier = Modifier,
    list: List<Cast>
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Cast",
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
            style = MaterialTheme.typography.h5
        )
        LazyRow {
            itemsIndexed(items = list) { index, cast ->
                CastItem(
                    profilePath = getImageUrl(cast.profilePath ?: ""),
                    name = cast.name,
                    character = cast.character,
                    modifier = Modifier.padding(
                        start = if (index == 0) 16.dp else 4.dp,
                        end = if (index == list.size - 1) 16.dp else 4.dp
                    )
                )
            }
        }
    }
}

@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    profilePath: String,
    name: String,
    character: String
) {
    Column(
        modifier = modifier.preferredWidth(width = 84.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarImage(
            path = profilePath,
            modifier = Modifier.padding(vertical = 12.dp).preferredSize(64.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
        Text(
            text = character,
            style = MaterialTheme.typography.subtitle2.copy(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            ),
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}