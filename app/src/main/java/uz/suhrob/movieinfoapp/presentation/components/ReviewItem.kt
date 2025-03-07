package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReviewItem(
    authorName: String,
    avatarUrl: String,
    content: String
) {
    var textCollapsed by remember(content) { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarImage(path = avatarUrl, modifier = Modifier.size(48.dp))
                Text(text = authorName, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { textCollapsed = !textCollapsed }) {
                    Icon(
                        imageVector = if (textCollapsed) Icons.Filled.KeyboardArrowUp
                        else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            HorizontalDivider()
            Text(
                text = if (textCollapsed) content else getFirstSentence(content),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun getFirstSentence(string: String): String {
    return string.split(".")[0] + "."
}