package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.other.loadImage
import uz.suhrob.movieinfoapp.R

@Composable
fun ReviewItem(
    authorName: String,
    avatarUrl: String,
    content: String
) {
    val textCollapsed = mutableStateOf(true)
    Card(modifier = Modifier.fillMaxWidth().padding(all = 8.dp)) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val image by loadImage(url = avatarUrl, defaultImage = R.drawable.profile_placeholder)
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        modifier = Modifier.preferredSize(48.dp).clip(CircleShape)
                    )
                }
                Text(text = authorName, style = MaterialTheme.typography.h6)
            }
            Text(text = if (textCollapsed.value) content.substring(0..100) else content, style = MaterialTheme.typography.body1)
            Text(
                text = if (textCollapsed.value) "Expand" else "Collapse",
                modifier = Modifier.clickable {
                    textCollapsed.value = !textCollapsed.value
                }
            )
        }
    }
}