package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.other.loadImage

@Composable
fun ReviewItem(
    authorName: String,
    avatarUrl: String,
    content: String
) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val image by loadImage(url = avatarUrl, defaultImage = 1)
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        modifier = Modifier.preferredSize(48.dp).clip(CircleShape)
                    )
                }
                Text(text = authorName, style = MaterialTheme.typography.h6)
            }
            Text(text = content, style = MaterialTheme.typography.body1)
        }
    }
}